package com.example.aicaiuser.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;

/**
 * @author niuhao
 * @date 2025/1/13 20:10
 */
@Service
public class RabbitSendService implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 消息队列
     */
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Autowired(required = false)
    private IMessageStoreService messageStoreService;

    public RabbitSendService(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper){
        //设置回调为当前类对象
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * 发送消息到rabbitmq消息队列
     *
     * @param exchange 交换配置
     * @param routingKey    队列配置
     * @param messageWrapper 消息包装器对象
     * @throws Exception
     */
    public <T> void send(String exchange, String routingKey, MessageWrapper<T> messageWrapper) {
        messageWrapper.setExchange(exchange);
        messageWrapper.setRoutingKey(routingKey);
        if (!persistMessage(messageWrapper)) {
            logger.info("消息持久化失败,[messageId:{}]",messageWrapper.getId());
        }
        MessageBuilder messageBuilder = getMessageBuilder(messageWrapper);
        logger.info("发送MQ消息: {}", JSON.toJSONString(messageWrapper));
        //发送消息到消息队列
        rabbitTemplate.convertAndSend(exchange, routingKey, messageBuilder.build(), new CorrelationData(messageWrapper.getId()));
    }

    private <T> MessageBuilder getMessageBuilder(MessageWrapper<T> messageWrapper) {
        //构建回调id为uuid
        String messageId = messageWrapper.getId();
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setCorrelationId(messageId);
        messageProperties.setContentEncoding(StandardCharsets.UTF_8.displayName());
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        messageProperties.setMessageId(messageId);
        messageProperties.setTimestamp(new Date());
        MessageBuilder messageBuilder = null;
        try {
            messageBuilder = MessageBuilder.withBody(objectMapper.writeValueAsBytes(messageWrapper)).andProperties(messageProperties);
        } catch (JsonProcessingException e) {
            logger.info("序列化消息: [correlationId={}] 时出现异常: {},消息发送失败!",messageId,e.getMessage());
        }
        return messageBuilder;
    }

    /**
     * 持久化消息
     * @param messageWrapper
     * @param <T>
     * @return
     */
    private <T> boolean persistMessage(MessageWrapper<T> messageWrapper) {
        logger.info("messageStoreService:{}",JSON.toJSONString(messageStoreService));
        if (Objects.nonNull(messageStoreService)) {
            logger.info("持久化消息 [messageId:{}]",messageWrapper.getId());
            return messageStoreService.persist(messageWrapper);
        }
        return false;
    }



    /**
     * 发送消息
     * @param routingKey 路由键
     * @param messageWrapper 消息包装对象
     * @param <T>
     */
    public <T> void send(String routingKey,MessageWrapper<T> messageWrapper) {
        messageWrapper.setRoutingKey(routingKey);
        if (!persistMessage(messageWrapper)) {
            return;
        }
        MessageBuilder messageBuilder = getMessageBuilder(messageWrapper);
        if (Objects.isNull(messageBuilder)) {
            return;
        }
        logger.info("发送MQ消息: [{}] 到 routingKey: [{}]", JSON.toJSONString(messageBuilder),routingKey);
        this.rabbitTemplate.convertAndSend(routingKey,messageBuilder.build(),new CorrelationData(messageWrapper.getId()));
    }


    /**
     * 如果消息没有到达交换机,ack = false;
     * 如果消息正确到达交换机,ack = true;
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            if (Objects.nonNull(correlationData)) {
                logger.info("消息：{}消息正确到达交换机", correlationData.getId());
                if (Objects.nonNull(messageStoreService)) {
                    messageStoreService.setMessageStatus(correlationData.getId(),true,"消息正确到达交换机");
                }
            }
        } else {
            if (Objects.nonNull(correlationData)) {
                logger.info("消息：{}消息没有到达交换机", correlationData.getId());
                if (Objects.nonNull(messageStoreService)) {
                    messageStoreService.setMessageStatus(correlationData.getId(),false,"消息没有到达交换机");
                }
            }
        }

    }

    /**
     * 消息从交换机成功到达队列，则不会执行;
     * 消息从交换机未能成功到达队列，则会执行;
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        logger.info("消息由交换机推送到队列失败：{},replyCode:{},replyText:{},exchange:{},routingKey:{}", new String(message.getBody(), StandardCharsets.UTF_8), replyCode, replyText, exchange, routingKey);
        if (Objects.nonNull(messageStoreService)) {
            String ackMessage = String.format("消息由交换机推送到队列失败：%s,replyCode:%s,replyText:%s,exchange:%s,routingKey:%s", new String(message.getBody(), StandardCharsets.UTF_8), replyCode, replyText, exchange, routingKey);
            messageStoreService.setMessageStatus(message.getMessageProperties().getMessageId(),false,ackMessage);
        }
    }
}

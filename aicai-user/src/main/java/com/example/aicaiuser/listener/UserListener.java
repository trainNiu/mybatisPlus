package com.example.aicaiuser.listener;

import com.alibaba.fastjson.JSON;
import com.example.aicaiuser.entity.UserEntity;
import com.example.aicaiuser.rabbitmq.MessageWrapper;
import com.google.common.reflect.TypeToken;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author niuhao
 * @date 2025/1/13 20:20
 */
@Slf4j
@Component
public class UserListener {


    /**
     * 同步新老系统员工基础信息监听处理器
     * @param channel 通道
     * @param message 消息
     */
    @RabbitListener(
            containerFactory = "simpleRabbitListenerContainer",
            bindings = @QueueBinding(
                    exchange = @Exchange(value = "user-change"),
                    value = @Queue(value = "user-queue"),
                    key = "userBindKey"
            )
    )
    @RabbitHandler
    public void syncStaffProcessor(Channel channel, Message message) {
        try {
            log.info("接收到消息: {}",message.toString());
            MessageWrapper<UserEntity> content = JSON.parseObject(message.getBody(), new TypeToken<MessageWrapper<UserEntity>>(){}.getType());
            log.info("消息内容为：{}",JSON.toJSONString(content));

        } catch (Exception e) {
            log.info("消费消息: [correlationId={},body={}] 时出现异常: {}",message.getMessageProperties().getCorrelationId(), JSON.parse(message.getBody()),e.getMessage());
            try {
                channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
            } catch (IOException ioException) {
                log.info("消费消息: [[correlationId={},body={}] 失败丢弃消息时出现异常: {}]",message.getMessageProperties().getCorrelationId(), JSON.parse(message.getBody()),e.getMessage());
            }
        }
    }
}

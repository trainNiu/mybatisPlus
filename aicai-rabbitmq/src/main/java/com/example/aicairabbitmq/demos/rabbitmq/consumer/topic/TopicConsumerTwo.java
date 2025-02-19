package com.example.aicairabbitmq.demos.rabbitmq.consumer.topic;

import com.example.aicairabbitmq.demos.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

/**
 * @author niuhao
 * @date 2025/2/19 16:12
 */
public class TopicConsumerTwo {

    private static final String EXCHANGE_NAME = "topic_logs";
    private static final String QUEUE_NAME = "Q2";

    public static void main(String[] args) {
        //创建信道
        Channel channel = RabbitMqUtils.getChannel();
        try {
            //声明交换机
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
            //声明队列
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
            //队列绑定交换机
            channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"*.*.rabbit");
            channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"lazy.#");
            //消费消息
            channel.basicConsume(QUEUE_NAME,true,(consumerTag, message) -> {
                System.out.println("获得消息:" + new String(message.getBody()));
            },consumerTag -> {
                System.out.println("消息消费被中断");
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

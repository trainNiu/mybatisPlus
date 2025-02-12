package com.example.aicairabbitmq.demos.rabbitmq.consumer.workQueue;

import com.example.aicairabbitmq.demos.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

/**
 * 多个消费者轮询接收：工作模式
 * @author niuhao
 * @date 2025/2/12 11:36
 */
public class ConsumerTwo2 {

    private static final String QUEUE_NAME = "work";

    public static void main(String[] args) {
        Channel channel = RabbitMqUtils.getChannel();
        try {
            channel.basicConsume(QUEUE_NAME,true,(consumerTag,message)->{
                System.out.println("消费者2号："+new String(message.getBody()));
            },consumerTag->{
                System.out.println("消费者2号取消消费接口回调");
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

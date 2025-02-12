package com.example.aicairabbitmq.demos.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author niuhao
 * @date 2025/2/11 19:26
 */
public class Consumer {

    public static final String QUEUE_NAME = "hello";
    public static void main(String[] args) {
        //创建一个连接工厂
        ConnectionFactory factory =  new ConnectionFactory();
        factory.setHost("192.168.253.128");
        factory.setUsername("test");
        factory.setPassword("test");

        try{
            //创建连接
            Connection connection = factory.newConnection();
            //创建信道
            Channel channel = connection.createChannel();

            //消费消息的回调
            channel.basicConsume(QUEUE_NAME,true, (consumerTag, message) -> {
                System.out.println("接收到消息:" + new String(message.getBody()));
            }, (consumerTag) -> {
                System.out.println("消息被中断");
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

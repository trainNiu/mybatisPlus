package com.example.aicairabbitmq.demos.rabbitmq.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author niuhao
 * @date 2025/2/11 19:17
 */
public class Producer {

    //队列名称
    public static final String QUEUE_NAME = "hello";


    public static void main(String[] args) {
        //创建一个连接工厂
        ConnectionFactory factory =  new ConnectionFactory();
        factory.setHost("192.168.253.128");
        factory.setPort(5672);
        factory.setUsername("test");
        factory.setPassword("test");

        try{
            //创建连接
            Connection connection = factory.newConnection();
            //创建信道
            Channel channel = connection.createChannel();
            /**
             * 创建一个队列
             * 1.队列名称
             * 2.队列里面的消息是否持久化(默认为false,代表消息存储在内存中)
             * 3.该队列是否只供一个消费者进行消费,是否进行共享(true表示可以多个消费者消费)
             * 4.表示最后一个消费者断开连接以后,该队列是否自动删除(true表示自动删除)
             * 5.其他参数
             */
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);

            /**
             * 发送一个消息
             * 1.发送到那个交换机(空代表默认交换机)
             * 2.路由key
             * 3.其他的参数信息
             * 4.发送消息的消息体
             */
            String message = "hello rabbitmq";
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

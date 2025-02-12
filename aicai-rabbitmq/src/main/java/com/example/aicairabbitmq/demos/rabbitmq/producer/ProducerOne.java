package com.example.aicairabbitmq.demos.rabbitmq.producer;

import com.example.aicairabbitmq.demos.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;

/**
 * 封装获取信道公共类，获取连接，创建通道，创建队列，发布消息
 * @author niuhao
 * @date 2025/2/12 11:01
 */
public class ProducerOne {

    //队列名称
    public static final String QUEUE_NAME = "hello_one";
    public static void main(String[] args) {
        Channel channel = RabbitMqUtils.getChannel();
        try {
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);

            for (int i = 0; i < 10; i++) {
                channel.basicPublish("",QUEUE_NAME,null,("hello"+i).getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

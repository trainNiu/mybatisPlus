package com.example.aicairabbitmq.demos.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author niuhao
 * @date 2025/2/12 10:57
 */

public class RabbitMqUtils {
    public static Channel getChannel()  {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.253.128");
        factory.setPort(5672);
        factory.setUsername("test");
        factory.setPassword("test");
        Channel channel = null;
        try {
            Connection connection = factory.newConnection();
            channel = connection.createChannel();
        }catch (Exception e){
            e.printStackTrace();
        }
        return channel;
    }
}

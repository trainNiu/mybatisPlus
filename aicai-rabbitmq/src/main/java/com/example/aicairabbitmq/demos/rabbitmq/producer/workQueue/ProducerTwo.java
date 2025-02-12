package com.example.aicairabbitmq.demos.rabbitmq.producer.workQueue;

import com.example.aicairabbitmq.demos.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author niuhao
 * @date 2025/2/12 11:36
 */
public class ProducerTwo {

    public static final String QUEUE_NAME = "work";

    public static void main(String[] args) {
        Channel channel = RabbitMqUtils.getChannel();
        try {
            //声明队列
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
            //发送消息
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()){
                String message = scanner.next();
                channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
                System.out.println("发送消息："+message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

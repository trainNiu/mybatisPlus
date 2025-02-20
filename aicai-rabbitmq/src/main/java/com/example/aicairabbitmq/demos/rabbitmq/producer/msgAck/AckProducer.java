package com.example.aicairabbitmq.demos.rabbitmq.producer.msgAck;

import com.example.aicairabbitmq.demos.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.util.Scanner;

/**
 * @author niuhao
 * @date 2025/2/20 10:15
 */
public class AckProducer {

    public static String QUEUE_NAME = "ack";

    public static void main(String[] args) {
        //创建信道
        Channel channel = RabbitMqUtils.getChannel();
        try {
            //声明队列
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
            //发送消息
//            Scanner scanner = new Scanner(System.in);
//            while (scanner.hasNext()){
//                String message = scanner.next();
//                channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
//                System.out.println("消息发送完毕："+message);
//            }

            for (int i = 0; i < 10; i++) {
                channel.basicPublish("",QUEUE_NAME,null,("消息"+i).getBytes());
                System.out.println("发送消息："+i);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

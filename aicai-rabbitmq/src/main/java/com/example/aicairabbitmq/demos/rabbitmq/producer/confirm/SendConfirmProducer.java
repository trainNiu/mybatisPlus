package com.example.aicairabbitmq.demos.rabbitmq.producer.confirm;

import com.example.aicairabbitmq.demos.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;

/**
 * @author niuhao
 * @date 2025/2/20 17:59
 */
public class SendConfirmProducer {

    public static String QUEUE_NAME = "confirm";
    public static int MessageCount = 10;

    public static void main(String[] args) throws IOException, InterruptedException {
        //创建信道
        Channel channel = RabbitMqUtils.getChannel();
        //开启发布确认
        channel.confirmSelect();
        //开始时间
        long begin = System.currentTimeMillis();
        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //批量发送10条消息
        for (int i = 0; i < MessageCount; i++) {
            String message = "消息"+i;
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            //单个消息发送完毕马上确认
            boolean flag = channel.waitForConfirms();
            //服务端返回false 或者超时时间内未返回，生产者可以消息重发
            if (flag){
                System.out.println("消息发送成功："+message);
            }
        }

        //结束时间
        long end = System.currentTimeMillis();
        System.out.println("发布确认"+MessageCount+"个消息,耗时："+(end-begin)+"ms");

    }
}

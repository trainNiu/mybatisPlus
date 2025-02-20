package com.example.aicairabbitmq.demos.rabbitmq.producer.confirm;

import com.example.aicairabbitmq.demos.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;

/**
 * @author niuhao
 * @date 2025/2/20 17:59
 */
public class SendBatchConfirmProducer {

    public static String QUEUE_NAME = "confirm";
    public static int MessageCount = 10;

    public static void main(String[] args) throws IOException, InterruptedException {
       //创建信道
        Channel channel = RabbitMqUtils.getChannel();
        //开启发布确认
        channel.confirmSelect();
        //确定批量大小
        int batchSize = 5;
        //未确认消息个数
        int unConfirmCount = 0;
        //开始时间
        long begin = System.currentTimeMillis();
        //发送消息
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        for (int i = 0; i < MessageCount; i++) {
            String message = "消息"+i;
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            unConfirmCount++;
            if (unConfirmCount == batchSize){
                boolean flag = channel.waitForConfirms();
                if (flag){
                    System.out.println("消息发送成功："+message);
                }
                unConfirmCount = 0;

            }
        }
        //为了确保还有消息没有确认消息，再次确认
        if (unConfirmCount > 0){
            channel.waitForConfirms();
        }
        //结束时间
        long end = System.currentTimeMillis();
        System.out.println("发布确认"+MessageCount+"个消息,耗时："+(end-begin)+"ms");

    }
}

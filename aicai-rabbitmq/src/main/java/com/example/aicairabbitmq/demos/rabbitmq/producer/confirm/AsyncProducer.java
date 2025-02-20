package com.example.aicairabbitmq.demos.rabbitmq.producer.confirm;

import com.example.aicairabbitmq.demos.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;

import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author niuhao
 * @date 2025/2/20 19:41
 */
public class AsyncProducer {

    public static String QUEUE_NAME = "confirm";
    public static int MessageCount = 10;

    public static void main(String[] args) {
        //创建信道
        try {
            Channel channel = RabbitMqUtils.getChannel();

            //发布确认
            channel.confirmSelect();
             //开始时间
            long begin = System.currentTimeMillis();
            //准备一个线程安全有序的哈希表，用于存放消息的序号以及内容
            ConcurrentSkipListMap<Long,String> concurrentSkipListMap = new ConcurrentSkipListMap<>();
            //消息确认成功回调函数（第一个参数表示消息标识，第二个参数表示是否批量确认
            ConfirmCallback confirmCallback = (deliveryTag,multiple)->{
                //删除掉已经确认的消息，剩下的就是未确认的消息
                if (multiple){
                    //如果批量，则批量删除
                    concurrentSkipListMap.headMap(deliveryTag).clear();
                }else {
                    concurrentSkipListMap.remove(deliveryTag);
                }
                System.out.println("确认的消息："+deliveryTag);
            };
            //消息确认失败回调函数,第一个是消息标志，第二个是批量确认
            ConfirmCallback nackCallback = (deliveryTag,multiple)->{
                String message = concurrentSkipListMap.get(deliveryTag);
                System.out.println("未确认的消息："+message);
            };
            //首先准备异步消息监听器，监听哪些消息成功了，哪些消息失败了
            channel.addConfirmListener(confirmCallback,nackCallback);
            //发送消息
            for (int i = 0; i < MessageCount; i++) {
                String message = "消息"+i;
                channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
                concurrentSkipListMap.put(channel.getNextPublishSeqNo(),message);
            }
            //结束时间
            long end = System.currentTimeMillis();
            System.out.println("发布确认"+MessageCount+"个消息,耗时："+(end-begin)+"ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.example.aicairabbitmq.demos.rabbitmq.consumer.fanout;

import com.example.aicairabbitmq.demos.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

/**
 * @author niuhao
 * @date 2025/2/19 11:40
 */
public class FanoutConsumer {

    public static void main(String[] args) {
        //获取信道
        Channel channel = RabbitMqUtils.getChannel();
        try {
            //声明交换机
            channel.exchangeDeclare("log", BuiltinExchangeType.FANOUT);
            //声明临时队列
            String queue = channel.queueDeclare().getQueue();
            //交换机绑定队列
            channel.queueBind(queue,"log","");
            //消费消息
            channel.basicConsume(queue,true,(consumerTag,message)->{
                System.out.println("消费者1："+new String(message.getBody()));
            },consumerTag->{
                System.out.println("消费者1取消消费接口回调");
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

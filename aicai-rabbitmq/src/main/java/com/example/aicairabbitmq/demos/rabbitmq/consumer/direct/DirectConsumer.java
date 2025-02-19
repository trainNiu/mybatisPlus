package com.example.aicairabbitmq.demos.rabbitmq.consumer.direct;

import com.example.aicairabbitmq.demos.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

/**
 * @author niuhao
 * @date 2025/2/19 14:23
 */
public class DirectConsumer {

    private static final String QUEUE_NAME = "console";
    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) {
        //获取信道
        Channel channel = RabbitMqUtils.getChannel();

        try {
          //声明交换机
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            //声明临时队列
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
            //交换机绑定队列
            channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"info");
            channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"warning");

            channel.basicConsume(QUEUE_NAME,true,(consumerTag,message)->{
                System.out.println("消费者1："+new String(message.getBody()));
            },consumerTag->{
                System.out.println("消费者1取消消费接口回调");
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

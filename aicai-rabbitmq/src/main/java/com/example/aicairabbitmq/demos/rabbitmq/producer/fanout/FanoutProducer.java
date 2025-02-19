package com.example.aicairabbitmq.demos.rabbitmq.producer.fanout;

import com.example.aicairabbitmq.demos.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

/**
 * 扇出类型的交换机：发布订阅模式
 * @author niuhao
 * @date 2025/2/19 11:36
 */
public class FanoutProducer {

    public static void main(String[] args) {
        //获取信道
        Channel channel = RabbitMqUtils.getChannel();
        try {
            //声明交换机
            channel.exchangeDeclare("log", BuiltinExchangeType.FANOUT);
            //发送10条消息
            for (int i = 0; i < 10; i++) {
                String message = "日志信息："+i;
                channel.basicPublish("log","",null,message.getBytes());
                System.out.println("发送消息："+message);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

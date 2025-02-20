package com.example.aicairabbitmq.demos.rabbitmq.consumer.msgAck;

import com.example.aicairabbitmq.demos.utils.RabbitMqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;

/**
 * @author niuhao
 * @date 2025/2/20 10:15
 */
public class AckConsumer {

    public static String QUEUE_NAME = "ack";

    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMqUtils.getChannel();
        System.out.println("consumer1收到消息时间较短");
        //消费消息回调
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            //模拟接收消息的延迟 1s
            try {
                System.out.println("消息消费成功,内容为："+ new String(message.getBody()));
                //手动应答：第一个参数表示消息标记tag,第二个参数false标识不进行批量应答
                channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        CancelCallback cancelCallback = (consumerTag)->{
            System.out.println("消费者取消消费接口回调");
        };
        //消费消息
        //第二个参数false,表示手动应答
        channel.basicConsume(QUEUE_NAME,false,deliverCallback,cancelCallback);
    }
}

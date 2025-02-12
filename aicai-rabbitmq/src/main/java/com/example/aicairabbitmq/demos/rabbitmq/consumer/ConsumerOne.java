package com.example.aicairabbitmq.demos.rabbitmq.consumer;

import com.example.aicairabbitmq.demos.utils.RabbitMqUtils;

/**
 * 消费者 封装获取信道公共类
 * @author niuhao
 * @date 2025/2/12 11:05
 */
public class ConsumerOne {

    //队列名称
    public static final String QUEUE_NAME = "hello_one";

    public static void main(String[] args) {
        try {
            RabbitMqUtils.getChannel().basicConsume(QUEUE_NAME,true,(consumerTag, message)->{
                System.out.println("消费者1号："+new String(message.getBody()));
            },(consumerTag)->{
                System.out.println("消费者1号取消消费接口回调");
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

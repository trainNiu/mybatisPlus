package com.example.aicairabbitmq.demos.rabbitmq.producer.direct;

import com.example.aicairabbitmq.demos.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author niuhao
 * @date 2025/2/19 14:22
 */
public class DirectProducer {

    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) {
        //获取信道
        Channel channel = RabbitMqUtils.getChannel();

        try {
            //声明交换机
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            //发送消息
            Map<String,String> map = new HashMap<>();
            map.put("info","这是一条info信息");
            map.put("warning","这是一条warning信息");
            map.put("error","这是一条error信息");
            map.put("other", "这是一条other信息");
            for (Map.Entry<String, String> mes : map.entrySet()) {
                String key = mes.getKey();
                String value = mes.getValue();
                channel.basicPublish(EXCHANGE_NAME,key,null,value.getBytes());
                System.out.println("消息发送完毕："+value);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

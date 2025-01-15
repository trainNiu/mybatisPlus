package com.example.aicaiuser.rabbitmq;

/**
 * 消息持久化方法
 * @author hiro
 * @version 1.0
 * @date 2022-04-22 17:11
 */
public interface IMessageStoreService {

    /**
     * 持久化消息
     * @param messageWrapper MQ消息
     * @param <T> 消息体类型
     */
    <T> boolean persist(MessageWrapper<T> messageWrapper);

    /**
     * 根据确认信息设置消息的状态
     * @param messageId 确认消息
     * @param ack 是否签收
     * @param ackMessage 签收消息
     * @return
     */
    boolean setMessageStatus(String messageId,boolean ack,String ackMessage);
}

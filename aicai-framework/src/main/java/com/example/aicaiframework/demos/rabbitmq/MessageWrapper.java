package com.example.aicaiframework.demos.rabbitmq;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

/**
 * 消息包装器类
 * @author zhaohl
 * @since 2022-03-08 18:03:38
 */
@Getter
@Setter
@ToString
public class MessageWrapper<T> implements Serializable {

    /**
     * 消息唯一ID
     */
    private String id;
    /**
     * 时间戳,用于消息排序
     */
    private Long timestamp;
    /**
     * 版本号
     */
    private Integer version;
    /**
     * 业务类型; 与业务方约定
     */
    private Integer businessType;

    @JsonIgnore
    @JSONField(serialize = false)
    private transient String exchange;

    @JSONField(serialize = false)
    @JsonIgnore
    private transient String routingKey;

    /**
     * 消息数据
     */
    private T data;

    private MessageWrapper() {

    }

    private MessageWrapper(Integer businessType, T data) {
        this(businessType,data,1);
    }

    private MessageWrapper(Integer businessType, T data, Integer version) {
        this.id = UUID.randomUUID().toString().replaceAll("-","");
        this.timestamp = System.currentTimeMillis();
        this.businessType = businessType;
        this.data = data;
        this.version = version;
    }

    public static <T> MessageWrapper<T> wrap(T data) {
        return new MessageWrapper<>(null,data);
    }

    public static <T> MessageWrapper<T> wrap(Integer businessType,T data) {
        return new MessageWrapper<>(businessType,data);
    }

    public static <T> MessageWrapper<T> wrap(Integer businessType,T data,Integer version) {
        return new MessageWrapper<>(businessType,data,version);
    }
}

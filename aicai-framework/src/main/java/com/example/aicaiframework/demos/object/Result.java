package com.example.aicaiframework.demos.object;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.poi.ss.formula.functions.T;

import java.io.Serializable;

/**
 * RESTful接口统一返回结果对象封装
 * @author zhaohl
 * @since 2021-11-30 11:11:20
 */
@ToString
@Getter
@Setter
public final class Result<DATA> implements Serializable {

    /**
     * 响应码
     */
    private String code;
    /**
     * 响应信息
     */
    private String message;
    /**
     * 响应数据体
     */
    private DATA data;

    private T result;

    private Result() {

    }

    private Result(IResultCode resultCode) {
        this(resultCode, null);
    }

    private Result(IResultCode resultCode, DATA data) {
        this(resultCode.getCode(),resultCode.getMessage(),data);
    }

    private Result(String code, String message) {
        this(code,message,null);
    }

    /**
     * 禁止外部实例化
     * @param code    响应码
     * @param message 响应信息
     * @param data    数据体
     */
    private Result(String code, String message, DATA data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 判断响应结果是否成功
     * @return
     */
    @JSONField(serialize = false)
    @JsonIgnore
    public boolean isSuccess() {
        return ResultCode.SUCCESS.getCode().equals(this.code);
    }

    /**
     *
     * @param success
     * @return
     */
    public Result setSuccess(boolean success) {
        ResultCode rc = success ? ResultCode.SUCCESS : ResultCode.FAIL;
        this.setMessage(rc.getMessage());
        this.setCode(rc.getCode());
        return this;
    }

    /**
     * 错误信息
     * @param errorMsg
     * @return
     */
    public Result setFail(String errorMsg) {
        this.setCode(ResultCode.FAIL.getCode());
        this.setMessage(errorMsg);
        return this;
    }

    /**
     * 创建一个空的返回对象
     * @param <DATA>
     * @return
     */
    public static <DATA> Result<DATA> create() {
        return new Result<>();
    }

    public static <DATA> Result<DATA> create(IResultCode resultCode) {
        return new Result<>(resultCode);
    }

    /**
     * 不区分类型构建对象
     * @param resultCode 响应码
     * @param data 数据体
     * @param <DATA> 数据体类型
     * @return
     */
    public static <DATA> Result<DATA> create(IResultCode resultCode,DATA data) {
        return new Result<>(resultCode,data);
    }


    /**
     * 创建一个没有响应结果的返回对象
     * @param code 响应码
     * @param message 响应消息
     * @param <DATA> 响应数据类型
     * @return
     */
    public static <DATA> Result<DATA> create(String code,String message) {
        return new Result<>(code,message);
    }

    /**
     * 不区分类型创建返回对象
     * @param code 响应码
     * @param message 响应消息
     * @param data 响应数据体
     * @param <DATA> 响应数据体类型
     * @return
     */
    public static <DATA> Result<DATA> create(String code,String message,DATA data) {
        return new Result<>(code,message,data);
    }


    public static <DATA> Result<DATA> create(boolean success) {
        return new Result<>(success?ResultCode.SUCCESS:ResultCode.FAIL);
    }

    public static <DATA> Result<DATA> create(boolean success,String msg) {
        return Result.create(success ? ResultCode.SUCCESS.getCode() : ResultCode.FAIL.getCode(), msg);
    }

    /**
     * 返回成功结果,默认响应数据为 null
     * @param <DATA> 响应数据类型
     * @return
     */
    public static <DATA> Result<DATA> success() {
        return Result.success(null);
    }

    /**
     * 返回成功结果,并给定响应数据
     * @param data   响应数据体
     * @param <DATA> 响应数据类型
     * @return
     */
    public static <DATA> Result<DATA> success(DATA data) {
        return Result.create(ResultCode.SUCCESS,data);
    }

    /**
     * 返回失败结果,默认不携带数据体,即数据体为 null
     * @param <DATA> 响应数据类型
     * @return
     */
    public static <DATA> Result<DATA> fail() {
        return Result.fail(ResultCode.FAIL);
    }

    /**
     * 返回失败结果,并指定响应信息
     * @param message
     * @param <DATA>
     * @return
     */
    public static <DATA> Result<DATA> fail(String message) {
        return Result.fail(ResultCode.FAIL.getCode(),message);
    }

    /**
     * 请求路径错误
     * @param <DATA>
     * @return
     */
    public static <DATA> Result<DATA> pathError() {
        return Result.create(ResultCode.PATH_ERROR);
    }

    /**
     * 返回失败结果,指定响应码
     * @param resultCode 响应码
     * @param <DATA> 响应数据类型
     * @return
     */
    public static <DATA> Result<DATA> fail(IResultCode resultCode) {
        return Result.create(resultCode);
    }

    public static <DATA> Result<DATA> fail(IResultCode resultCode,String message) {
        return Result.create(resultCode.getCode(),message);
    }

    /**
     * 返回失败结果,并附带响应数据
     * @param resultCode 响应码
     * @param data 响应数据
     * @param <DATA>
     * @return
     */
    public static <DATA> Result<DATA> fail(IResultCode resultCode,DATA data) {
        return Result.create(ResultCode.FAIL,data);
    }

    /**
     * 返回失败结果,指定响应码和响应消息
     * @param code 响应码
     * @param message 响应信息
     * @param <DATA> 响应数据体类型
     * @return
     */
    public static <DATA> Result<DATA> fail(String code, String message) {
        return Result.create(code,message);
    }

    /**
     * 返回失败结果,指定响应码和响应消息
     * @param code 响应码
     * @param message 响应信息
     * @param <DATA> 响应数据体类型
     * @return
     */
    public static <DATA> Result<DATA> fail(String code,String message,DATA data) {
        return Result.create(code,message,data);
    }
}

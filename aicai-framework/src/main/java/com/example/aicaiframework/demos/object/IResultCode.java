package com.example.aicaiframework.demos.object;

/**
 * @author zhaohl
 * @since 2021-11-30 12:33:11
 */
public interface IResultCode {

    /**
     * 获取响应码
     * @return
     */
    String getCode();

    /**
     * 获取响应信息
     * @return
     */
    String getMessage();
}

package com.example.aicaiframework.demos.object;

/**
 * desc:系统基础响应码
 */
public enum ResultCode implements IResultCode{

    //处理成功
    SUCCESS("200", "处理成功"),
    //处理失败
    FAIL("201", "处理失败"),
    //请求路径错误
    PATH_ERROR("404", "请求路径错误"),
    //登录失效
    BAD_LOGIN("401","用户登录失效"),
    //未授权
    UNAUTHORIZED("403","无权限访问"),
    UNCERTIFIED("405","你尚未获得证书，无法使用当前功能！"),
    UNTASK("406","你尚未完成任务，无法使用当前功能！"),
    NO_DATA_ACCESS("407","未查询到数据或没有查看权限！"),
    //服务端错误
    UNKNOW("500", "服务端无法完成对请求的处理"),
    TIMEOUT("504","请求超时!"),
    //
    API_REQUEST("1001", "API访问参数错误"),
    REQUEST_LIMIT("1002","操作太过频繁,请稍后重试!"),
    ;



    /**
     * 枚举值
     */
    private String code;
    /**
     * 枚举描述
     */

    private String message;

    /**
     * @return
     */
    @Override
    public String getCode() {
        return this.code;
    }

    /**
     * @return
     */
    @Override
    public String getMessage() {
        return this.message;
    }

    /**
     * 构造方法
     *
     * @param code
     * @param message
     */
    private ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }


}

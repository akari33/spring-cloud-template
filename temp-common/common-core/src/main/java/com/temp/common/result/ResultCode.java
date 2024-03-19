package com.temp.common.result;

import lombok.Getter;

/**
 * API返回码封装类
 */
@Getter
public enum ResultCode implements IErrorCode {
    SUCCESS("A0200", "success"),
    FAILED("A0500", "fail"),
    VALIDATE_FAILED("A0404", "validate_failed"),
    UNAUTHORIZED("A0401", "unauthorized"),
    FORBIDDEN("A0403", "forbidden"),
    USER_NOT_EXIST("A0201", "user not exist"),


    /**
     * to many request
     */
    FLOW_LIMITING("A0429", "flow limiting"),
    /**
     * 访问未授权
     */
    ACCESS_UNAUTHORIZED("A0301", "access unauthorized"),

    /**
     * 资源未找到
     */
    RESOURCE_NOT_FOUND("A0401", "resource not found"),

    TOKEN_INVALID("A0230", "token无效或已过期"),
    TOKEN_ACCESS_FORBIDDEN("A0231", "token已被禁止访问"),
    REPEAT_SUBMIT_ERROR("A0303", "您的请求已提交，请不要重复提交或等待片刻再尝试。"),

    ;

    private String code;
    private String message;

    private ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

}

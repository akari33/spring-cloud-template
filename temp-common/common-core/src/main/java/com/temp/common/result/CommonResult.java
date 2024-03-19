package com.temp.common.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;

/**
 * 通用返回结果封装类
 */
@Schema(description = "通用返回结果封装类")
@Data
public class CommonResult<T> {

    @Schema(description = "是否成功")
    private boolean success;

    @Schema(description = "错误码")
    private String errorCode;

    @Schema(description = "错误信息")
    private String errorMessage;

    @Schema(description = "跟踪ID")
    private String traceId;

    @Schema(description = "数据封装")
    private T data;

    protected CommonResult() {
    }

    protected CommonResult(boolean success, String errorCode, String errorMessage, String traceId, T data) {
        this.success = success;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.traceId = traceId;
        this.data = data;
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(true, null, null, TraceContext.traceId(), data);
    }

    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     */
    public static <T> CommonResult<T> failed(IErrorCode errorCode) {
        return new CommonResult<>(false, errorCode.getCode(), errorCode.getMessage(), TraceContext.traceId(), null);
    }

    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     * @param message   错误信息
     */
    public static <T> CommonResult<T> failed(IErrorCode errorCode, String message) {
        return new CommonResult<>(false, errorCode.getCode(), message, TraceContext.traceId(), null);
    }

    /**
     * 失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> CommonResult<T> failed(String message) {
        return new CommonResult<>(false, ResultCode.FAILED.getCode(), message, TraceContext.traceId(), null);
    }

    /**
     * 失败返回结果
     */
    public static <T> CommonResult<T> failed() {
        return failed(ResultCode.FAILED);
    }


    /**
     * 参数验证失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> CommonResult<T> validateFailed(String message) {
        return new CommonResult<>(false, ResultCode.VALIDATE_FAILED.getCode(), message, TraceContext.traceId(), null);
    }
}

package com.xiao.pharmacy.common.entity.Api;

import lombok.Data;

/**
 * @Author: xiaoxiongwen
 * @Date: 2023/09/14/17:11
 * @Description:
 */
@Data
public class ApiResult<T> {
    /**
     * 成功还是失败
     **/
    private boolean success = true;

    /**
     * 返回code
     */
    private long code;

    /**
     * 返回的信息
     */
    private String message;

    /**
     * 返回的数据
     */
    private T data;

    public ApiResult() {

    }

    public ApiResult(boolean success, long code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功返回
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<T>(true, ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回
     *
     * @param <T>
     * @return
     */
    public static <T> ApiResult<T> success() {
        return new ApiResult<T>(true, ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }

    /**
     * 成功返回
     *
     * @param data
     * @param message
     * @param <T>
     * @return
     */
    public static <T> ApiResult<T> success(T data, String message) {
        return new ApiResult<>(true, ResultCode.SUCCESS.getCode(), message, data);
    }


    /**
     * 失败返回
     *
     * @param message
     * @param <T>
     * @return
     */
    public static <T> ApiResult<T> failed(String message) {
        return new ApiResult<>(false, ResultCode.FAILED.getCode(), message, null);
    }

    /**
     * 失败返回
     *
     * @param message
     * @param <T>
     * @return
     */
    public static <T> ApiResult<T> failed(String message, T data) {
        return new ApiResult<>(false, ResultCode.FAILED.getCode(), message, data);
    }

    /**
     * 失败返回，支持国际化
     *
     * @param resultCode
     * @param <T>
     * @return
     */
    public static <T> ApiResult<T> failed(ResultCode resultCode) {
        return failed(resultCode, null);
    }

    /**
     * 失败返回，支持国际化
     *
     * @param resultCode
     * @param <T>
     * @return
     */
    public static <T> ApiResult<T> failed(ResultCode resultCode, Object[] args) {
        //todo
        //return new ApiResult<>(false, resultCode.getCode(), MessageUtil.getCompareMessage(resultCode.getMessage(), resultCode.getHkMessage(), resultCode.getEnMessage(), args), null);
        return new ApiResult<>(false,resultCode.getCode(),null,null);
    }
}

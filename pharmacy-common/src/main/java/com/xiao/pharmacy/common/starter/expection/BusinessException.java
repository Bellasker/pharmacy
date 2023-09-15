package com.xiao.pharmacy.common.starter.expection;


import com.xiao.pharmacy.common.entity.api.ResultCode;

/**
 * @Author: xiaoxiongwen
 * @Date: 2023/06/01/13:53
 * @Description:
 */
public class BusinessException extends RuntimeException {

    private ResultCode resultCode;

    public ResultCode getResultCode() {
        return resultCode;
    }

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public static BusinessException of(ResultCode resultCode) {
        return new BusinessException(resultCode);
    }

    public static BusinessException of(String message) {
        return new BusinessException(message);
    }
}

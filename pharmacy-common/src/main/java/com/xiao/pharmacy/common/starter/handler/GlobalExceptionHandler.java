package com.xiao.pharmacy.common.starter.handler;


import com.xiao.pharmacy.common.entity.api.ApiResult;
import com.xiao.pharmacy.common.entity.api.ResultCode;
import com.xiao.pharmacy.common.starter.expection.BusinessException;
import com.xiao.pharmacy.common.tool.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;


/**
 * @Author: xiaoxiongwen
 * @Date: 2023/06/01/13:51
 * @Description:
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private ApiResult<?> handleMessage(String defaultMessage) {
        ResultCode resultCode = ResultCode.getResultCode(defaultMessage);
        if (resultCode != null) {
            return ApiResult.failed(resultCode);
        }
        if (StringUtils.isNotBlank(defaultMessage)) {
            return ApiResult.failed(defaultMessage);
        }
        return ApiResult.failed("Internal server error");
    }

    private String handleI18NCode(String defaultMessage) {
        if (StringUtils.isNotBlank(defaultMessage) && defaultMessage.startsWith("{") && defaultMessage.endsWith("}")) {
            defaultMessage = defaultMessage.replace("{", "");
            defaultMessage = defaultMessage.replace("}", "");
        }
        return defaultMessage;
    }

    /**
     * 业务异常拦截
     * 国际化使用方式：1->使用ResultCode，格式为BusinessException.of(ResultCode.FAILED)
     * 2->使用i18ncode值对应i18n配置文件code，格式为BusinessException.of("{FAIL}")
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    public ApiResult<?> handle(BusinessException e) {
        log.error(" ==> BusinessException", e);
        String defaultMessage = e.getMessage();
        defaultMessage = handleI18NCode(defaultMessage);
        ResultCode resultCode = e.getResultCode();
        if (resultCode != null) {
            defaultMessage = resultCode.name();
        }
        return handleMessage(defaultMessage);
    }

    /**
     * @param e
     * @return
     * @Valid方式实体类字段参数校验异常拦截 国际化使用方式：1->使用ResultCode，格式为@NotBlank(message = "ADMIN_EMAIL_NULL_ERROR")
     * 2->使用i18ncode值对应i18n配置文件code，格式为@Email(message = "{EMAIL_FORMAT_ERROR}")
     */
    @ExceptionHandler
    public ApiResult<?> handle(MethodArgumentNotValidException e) {
        String defaultMessage = e.getBindingResult().getFieldError().getDefaultMessage();
        return handleMessage(defaultMessage);
    }

    /**
     * @param e
     * @return
     * @Validated方式实体类字段参数校验异常拦截 国际化使用方式：1->使用ResultCode，格式为@NotBlank(message = "ADMIN_EMAIL_NULL_ERROR")
     * 2->使用i18ncode值对应i18n配置文件code，格式为@Email(message = "{EMAIL_FORMAT_ERROR}")
     */
    @ExceptionHandler
    public ApiResult<?> handle(ConstraintViolationException e) {
        String[] split = e.getMessage().split(",");
        String defaultMessage = split[0];
        defaultMessage = defaultMessage.substring(defaultMessage.indexOf(":") + 1).trim();
        defaultMessage = handleI18NCode(defaultMessage);
        return handleMessage(defaultMessage);
    }

    @ExceptionHandler
    public ApiResult<?> handle(Exception e) {
        log.error(" ==> Exception", e);
        return ApiResult.failed("Internal server error");
    }
}

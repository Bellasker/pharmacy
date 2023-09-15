package com.xiao.pharmacy.core.aspect;

import cn.dev33.satoken.stp.StpUtil;
import com.xiao.pharmacy.common.entity.api.ApiResult;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;
import com.xiao.pharmacy.common.starter.expection.BusinessException;

import static com.xiao.pharmacy.common.entity.api.ResultCode.LOG_OUT;


/**
 * @Author: xiaoxiongwen
 * @Date: 2023/09/14/16:46
 * @Description:
 */
@Order(-1)
@Component
@Aspect
public class CheckLogin {

    @Pointcut("execution(* com.xiao.pharmacy.core.controller.*.*(..))")
    private void point(){

    }

    @Order(-1)
    @Before("point()")
    public ApiResult  isLogin(JoinPoint joinPoint){
        System.out.println("前置："+joinPoint);
        if(!StpUtil.isLogin()){
            throw BusinessException.of(LOG_OUT);
        }
        return ApiResult.success();
    }
}

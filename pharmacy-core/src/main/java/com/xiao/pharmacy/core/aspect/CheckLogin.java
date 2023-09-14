package com.xiao.pharmacy.core.aspect;

import cn.dev33.satoken.stp.StpUtil;
import com.xiao.pharmacy.common.entity.Api.ApiResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;

import static com.xiao.pharmacy.common.entity.Api.ResultCode.LOG_OUT;


/**
 * @Author: xiaoxiongwen
 * @Date: 2023/09/14/16:46
 * @Description:
 */
@Order(-1)
@Component
@Aspect
public class CheckLogin {

    @Pointcut("execution(* com.xiao.pharmacy.core.controller.test.test())")
    private void point(){

    }

    @Order(-1)
    @Before("point()")
    public ApiResult isLogin(){
        if(StpUtil.isLogin()){
            ApiResult.failed(LOG_OUT);
        }
        return ApiResult.success();
    }
}

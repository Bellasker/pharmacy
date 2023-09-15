package com.xiao.pharmacy.common.starter.aspect;

import com.alibaba.fastjson.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;


import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 日志切面，用来输出参数
 */
@Order(-1)
@Aspect
@Component
public class LoggerAspect {
    public static final Map<String, String[]> methodParamNameMap = new ConcurrentHashMap<>();

    private static Logger logger = LoggerFactory.getLogger(LoggerAspect.class);

    @Around("execution(* com.xiao..*.controller..*.*(..))")
    public Object controllerMethodPointcut(ProceedingJoinPoint point) throws Throwable {
        try {
            logger.info("【{}】，参数如下：", getMethodName(point));
            String[] paramNames = getParamNames(point);
            Object[] args = point.getArgs();
            for (int i = 0; i < args.length; i++) {
                if (!isInstance(args[i], HttpServletRequest.class, HttpServletResponse.class, BindingResult.class, MultipartFile.class, MultipartFile[].class)) {
                    if (paramNames.length == args.length) {
                        logger.info("{} -> {}", paramNames[i], JSON.toJSONString(args[i]));
                    } else {
                        logger.info("参数{} -> {}", i, JSON.toJSONString(args[i]));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("请求参数打印异常，堆栈轨迹如下：", e);
        }
        return point.proceed();
    }

    /**
     * 判断对象是否属于数组中某一个类的实例
     */
    private boolean isInstance(Object obj, Class<?>... classes) {
        return Arrays.stream(classes).anyMatch(clazz -> clazz.isInstance(obj));
    }

    /**
     * 获得方法名 示例：com.workorder.controller.UmsMemberController.login
     */
    private String getMethodName(ProceedingJoinPoint point) {
        return point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName();
    }

    /**
     * 从缓存中获得方法的参数名
     */
    private String[] getParamNames(ProceedingJoinPoint point) {
        String methodName = getMethodName(point);
        if (!methodParamNameMap.containsKey(methodName)) {
            synchronized (this) {
                if (!methodParamNameMap.containsKey(methodName)) {
                    Method method = findMethod(point.getTarget().getClass(), point.getSignature().getName());
                    methodParamNameMap.put(methodName, getParamNames(method));
                }
            }
        }
        return methodParamNameMap.get(methodName);
    }

    /**
     * 通过方法名获得方法，不存在则返回 null
     */
    private Method findMethod(Class<?> clazz, String methodName) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> Objects.equals(method.getName(), methodName))
                .findAny().orElse(null);
    }

    /**
     * 获得方法中的所有参数名
     */
    private String[] getParamNames(Method method) {
        if (method == null) {
            return new String[]{};
        }
        return new LocalVariableTableParameterNameDiscoverer().getParameterNames(method);
    }

}

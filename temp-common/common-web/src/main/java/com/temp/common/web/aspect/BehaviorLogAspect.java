package com.temp.common.web.aspect;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.temp.common.security.util.SecurityUtils;
import com.temp.common.util.JsonUtil;
import com.temp.common.web.model.WebLog;
import com.temp.common.web.util.RequestUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.marker.Markers;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统一日志处理切面
 */
@Slf4j
@Aspect
@Component
@Order(1)
public class BehaviorLogAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(BehaviorLogAspect.class);

    @Pointcut("(execution(public * com.temp.*.controller.*.*(..)))")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
    }

    @AfterReturning(value = "webLog()", returning = "ret")
    public void doAfterReturning(Object ret) throws Throwable {
    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        //获取当前请求对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //记录请求信息(通过Logstash传入Elasticsearch)
        WebLog webLog = new WebLog();
        Object result = joinPoint.proceed();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method.isAnnotationPresent(Operation.class)) {
            Operation log = method.getAnnotation(Operation.class);
            webLog.setDescription(log.summary());
        }
        long endTime = System.currentTimeMillis();
        String urlStr = request.getRequestURL().toString();
        webLog.setBasePath(StrUtil.removeSuffix(urlStr, URLUtil.url(urlStr).getPath()));
        webLog.setUsername(SecurityUtils.getUserId() != null ? String.valueOf(SecurityUtils.getUserId()) : "anonymous");
        webLog.setIp(RequestUtil.getRequestIp(request));
        webLog.setMethod(request.getMethod());
        webLog.setParameter(setParameter(RequestUtil.getParameter(method, joinPoint.getArgs())));
        webLog.setResult(setResult(result));
        webLog.setSpendTime((int) (endTime - startTime));
        webLog.setStartTime(startTime);
        webLog.setUri(request.getRequestURI());
        webLog.setUrl(request.getRequestURL().toString());
        Map<String, Object> logMap = new HashMap<>();
        logMap.put("url", webLog.getUrl());
        logMap.put("method", webLog.getMethod());
        logMap.put("parameter", webLog.getParameter());
        logMap.put("spendTime", webLog.getSpendTime());
        logMap.put("description", webLog.getDescription());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        LOGGER.info("{}", logMap);
//        LOGGER.info(Markers.appendEntries(logMap), objectMapper.writeValueAsString(webLog));
        LOGGER.debug(Markers.appendEntries(logMap), objectMapper.writeValueAsString(webLog));
        return result;
    }


    /**
     * 将result里的字段转换成string
     */
    private String setResult(Object result) {
        if (result == null) {
            return null;
        }
        //返回都是commonResult,将data转换成string即可
        Field[] fields = result.getClass().getDeclaredFields();
        for (Field field : fields) {
            if ("data".equals(field.getName())) {
                field.setAccessible(true);
                try {
                    Object data = field.get(result);
                    if (data != null) {
                        return JsonUtil.toJSONString(data);
                    }
                } catch (IllegalAccessException e) {
                    log.error("setResult error", e);
                }
            }
        }
        return null;
    }


    /**
     * 将parameter里的字段转换成string
     */
    private String setParameter(Object parameter) {
        if (parameter == null) {
            return null;
        }
        try {
            //如果是List
            if (parameter instanceof List paramsList) {
                //剔除掉request和response
                List<Object> outPutList = new ArrayList<>();
                for (Object param : paramsList) {
                    if (!(param instanceof HttpServletResponse || param instanceof HttpServletRequest)) {
                        outPutList.add(param);
                    }
                }
                return JsonUtil.toJSONString(outPutList);
            }
            return JsonUtil.toJSONString(parameter);
        } catch (Exception e) {
            log.error("log setParameter error", e);
        }
        return null;
    }

}

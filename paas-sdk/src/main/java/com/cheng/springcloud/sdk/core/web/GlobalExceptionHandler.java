package com.cheng.springcloud.sdk.core.web;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class GlobalExceptionHandler{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 全局异常处理
	 * @param ex 异常
	 * @param request web请求
	 * @return 全局异常实体
	 */
	@ExceptionHandler({Exception.class})
    public GlobalExceptionResponse handleAll(Exception ex, WebRequest request) {
		logger.info("全局异常处理");
		ex.printStackTrace();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;    
        return new GlobalExceptionResponse(request.getDescription(false),status.name(),ex.getMessage(),ExceptionUtils.getStackTrace(ex));

    }
}

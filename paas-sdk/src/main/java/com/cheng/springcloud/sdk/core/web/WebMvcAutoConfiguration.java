package com.cheng.springcloud.sdk.core.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableConfigurationProperties
public class WebMvcAutoConfiguration extends WebMvcConfigurerAdapter{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 返回Etag返回头
	 * @return 返回Etag过滤器
	 */
	@Bean
	public ShallowEtagHeaderFilter etagHeaderfilter() {
		ShallowEtagHeaderFilter etagHeaderfilter = new ShallowEtagHeaderFilter();
		logger.info("ShallowEtagHeaderFilter创建成功!");
		return etagHeaderfilter;
	}
	
	/**
	 * 全局异常处理
	 * @return 全局异常处理bean
	 */
	@Bean
	public GlobalExceptionHandler globalExceptionHandler() {
		GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
		logger.info("GlobalExceptionHandler创建成功!");
		return globalExceptionHandler;
	}
	
}

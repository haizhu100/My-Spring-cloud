package com.cheng.springcloud.sdk.core.flowcontrol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author haizhu_cheng@163.com
 * @version Revision 1.0.0
 * @版权 版权所有 (c) 2018
 * @创建日期 2019年1月15日
 * @功能说明 流控对象自动配置类
 */
@Configuration
@EnableConfigurationProperties(FlowControlConfig.class)
public class FlowControlAutoConfiguration {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Bean
	public FlowControlFilter flowControlfilter() {

		FlowControlFilter flowControlfilter = new FlowControlFilter();

		logger.info("PaasFlowControlFilter创建成功!");

		return flowControlfilter;
	}
	
	@Bean
	public CacheFlowControlInfo getCacheFlowControlInfo() {
		CacheFlowControlInfo cacheFlowControlInfo = new CacheFlowControlInfo(0, 0);
		logger.info("CacheFlowControlInfo创建成功!");
		return cacheFlowControlInfo;
	}
}

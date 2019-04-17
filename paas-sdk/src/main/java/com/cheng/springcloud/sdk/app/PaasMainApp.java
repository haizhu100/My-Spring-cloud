package com.cheng.springcloud.sdk.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;

import com.cheng.springcloud.sdk.core.flowcontrol.EnableFlowControl;
import com.cheng.springcloud.sdk.core.swagger2.EnableSwagger2;
import com.cheng.springcloud.sdk.core.web.EnableWebMvc;

/**
 * @author haizhu_cheng@163.com
 * @version Revision 1.0.0
 * @版权 版权所有 (c) 2018
 * @创建日期 2019年4月11日
 * @功能说明 TODO
 */
@SpringBootApplication
@EnableSwagger2
@EnableFlowControl
@EnableWebMvc
@EnableDiscoveryClient
@ComponentScan(value = {"${service.app.basepkg}"}, excludeFilters = {
		@Filter(type = FilterType.REGEX, pattern = "com\\.cheng\\.springcloud\\.core.*") })
public class PaasMainApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(PaasMainApp.class, args);
	}

}

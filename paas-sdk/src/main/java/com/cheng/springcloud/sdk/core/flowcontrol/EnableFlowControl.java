package com.cheng.springcloud.sdk.core.flowcontrol;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

/**
 * @author haizhu_cheng@163.com
 * @version Revision 1.0.0
 * @版权 版权所有 (c) 2018
 * @创建日期 2019年1月15日
 * @功能说明 启用精准流控功能注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(FlowControlAutoConfiguration.class)
public @interface EnableFlowControl {
       
}

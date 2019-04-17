package com.cheng.springcloud.sdk.core.swagger2;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

/**
 * @author haizhu_cheng@163.com
 * @version Revision 1.0.0
 * @版权 版权所有 (c) 2018
 * @创建日期 2019年3月15日
 * @功能说明 开启swagger自动配置
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
@Import({Swagger2.class,SwaggerAutoConfiguration.class})
public @interface EnableSwagger2 {

}

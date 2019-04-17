package com.cheng.springcloud.sdk.core.swagger2;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author haizhu_cheng@163.com
 * @version Revision 1.0.0
 * @版权 版权所有 (c) 2018
 * @创建日期 2019年3月12日
 * @功能说明 PaasSwagger2配置文件
 */
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {
	
	/**
	 * swagger开关
	 */
	private boolean enabled = false;	
	
	/**
	 * 配置swagger的文档类型
	 */
	private String documentationType = "SWAGGER_2";
	
	/**
	 * 应用api文档单个显示
	 */
	private Map<String,SwaggerDocketProperties> dockets;


	public String getDocumentationType() {
		return documentationType;
	}

	public void setDocumentationType(String documentationType) {
		this.documentationType = documentationType;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Map<String, SwaggerDocketProperties> getDockets() {
		return dockets;
	}

	public void setDockets(Map<String, SwaggerDocketProperties> dockets) {
		this.dockets = dockets;
	}
}

package com.cheng.springcloud.sdk.core.swagger2;

/**
 * @author haizhu_cheng@163.com
 * @version Revision 1.0.0
 * @版权 版权所有 (c) 2018
 * @创建日期 2019年3月12日
 * @功能说明 swagger Docket配置文件
 */
public class SwaggerDocketProperties {

	private String basePackage;

	private String groupName;

	private String host;
	
	/**
	 * url显示配置
	 */
	private PathSelectorsProperties pathSelectors;
	
	private SwaggerApiInfoProperties apiinfo;

	public String getBasePackage() {
		return basePackage;
	}

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public SwaggerApiInfoProperties getApiinfo() {
		return apiinfo;
	}

	public void setApiinfo(SwaggerApiInfoProperties apiinfo) {
		this.apiinfo = apiinfo;
	}

	public PathSelectorsProperties getPathSelectors() {
		return pathSelectors;
	}

	public void setPathSelectors(PathSelectorsProperties pathSelectors) {
		this.pathSelectors = pathSelectors;
	}
}

package com.cheng.springcloud.sdk.core.swagger2;

import java.util.HashMap;
import java.util.Map;

/**
 * @author haizhu_cheng@163.com
 * @version Revision 1.0.0
 * @版权 版权所有 (c) 2018
 * @创建日期 2019年3月12日
 * @功能说明 swagger2 apiinfo信息
 */
public class SwaggerApiInfoProperties {
	// apiInfo

	private String title;

	private String description;

	private String termsOfServiceUrl;
	// 包含了contackname,contacturl,contactEmail 三个key
	private Map<String, String> contact = new HashMap<>();

	private String contactName;

	private String contactUrl;

	private String contactEmail;

	private String license;

	private String licenseUrl;

	private String version;

	/**
	 * swagger2的标准
	 */
	private String xAudience;

	/**
	 * swagger2的标准
	 */
	private String xApiId;

	public String getxAudience() {
		return xAudience;
	}

	public void setxAudience(String xAudience) {
		this.xAudience = xAudience;
	}

	public String getxApiId() {
		return xApiId;
	}

	public void setxApiId(String xApiId) {
		this.xApiId = xApiId;
	}

	public Map<String, String> getContact() {
		return contact;
	}

	public void setContact(Map<String, String> contact) {
		this.contact = contact;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTermsOfServiceUrl() {
		return termsOfServiceUrl;
	}

	public void setTermsOfServiceUrl(String termsOfServiceUrl) {
		this.termsOfServiceUrl = termsOfServiceUrl;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactUrl() {
		return contactUrl;
	}

	public void setContactUrl(String contactUrl) {
		this.contactUrl = contactUrl;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getLicenseUrl() {
		return licenseUrl;
	}

	public void setLicenseUrl(String licenseUrl) {
		this.licenseUrl = licenseUrl;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}

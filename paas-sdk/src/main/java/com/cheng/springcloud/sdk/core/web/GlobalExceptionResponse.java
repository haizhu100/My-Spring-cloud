package com.cheng.springcloud.sdk.core.web;

public class GlobalExceptionResponse{
	/**
	 * 具体错误代码
	 */
	private String code;
	
	/**
	 * 具体错误说明
	 */
	private String reason;
	
	/**
	 * 更详细的错误说明
	 */
	private String message;
	
	/**
	 * 用于描述具体错误的页面地址
	 */
	private String referenceError;
	
	public GlobalExceptionResponse(String code,String reason,String message,String referenceError) {
		this.code = code;
		this.reason = reason;
		this.message = message;
		this.referenceError = referenceError;
	}

	public String getCode() {
		return code;
	}

	public String getReason() {
		return reason;
	}

	public String getMessage() {
		return message;
	}

	public String getReferenceError() {
		return referenceError;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setReferenceError(String referenceError) {
		this.referenceError = referenceError;
	}
}

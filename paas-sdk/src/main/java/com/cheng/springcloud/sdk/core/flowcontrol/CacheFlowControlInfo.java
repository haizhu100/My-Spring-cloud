package com.cheng.springcloud.sdk.core.flowcontrol;


public class CacheFlowControlInfo {

	// 分布式缓存的 时间key
	private Long apptime;
	// 分布式缓存的计数key
	private int appcount;

	public CacheFlowControlInfo(long apptime,int appcount) {
		this.appcount = appcount;
		this.apptime = apptime;
	}
	public int getAppcount() {
		return appcount;
	}

	public void setAppcount(int appcount) {
		this.appcount = appcount;
	}

	public Long getApptime() {
		return apptime;
	}

	public void setApptime(Long apptime) {
		this.apptime = apptime;
	}
}

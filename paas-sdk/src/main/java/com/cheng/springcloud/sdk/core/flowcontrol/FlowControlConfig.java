package com.cheng.springcloud.sdk.core.flowcontrol;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author haizhu_cheng@163.com
 * @version Revision 1.0.0
 * @版权 版权所有 (c) 2018
 * @创建日期 2019年1月15日
 * @功能说明 流控启动配置
 */
@ConfigurationProperties(prefix = "flowcontrol")
public class FlowControlConfig {
		
		//限流策略中的T时间内最大访问量(阈值).	
		private int threshold = 50;
		
		//配置文件中配置 限流策略中出现的时间间隔(周期))，注意配置的时候按秒计数
		private long interval = 5;
		
		//配置文件中配置不同实例的分布式锁名
		private String lockname = "democonsumer";
		
		public String getLockname() {
			return lockname;
		}

		public void setLockname(String lockname) {
			this.lockname = lockname;
		}

		public int getThreshold() {
			return threshold;
		}

		public void setThreshold(int threshold) {
			this.threshold = threshold;
		}

		
		public long getInterval() {
			return interval;
		}

		public void setInterval(long interval) {
			this.interval = interval;
		}
}

package com.cheng.springcloud.sdk.core.flowcontrol;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import com.google.common.util.concurrent.RateLimiter;

/**
 * @author haizhu_cheng@163.com
 * @version Revision 1.0.0
 * @版权 版权所有 (c) 2018
 * @创建日期 2019年1月15日
 * @功能说明 流控过滤器
 */
public class FlowControlFilter extends OncePerRequestFilter {

	private Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 客户端在特定时间窗⼝口中可以被允许使⽤用的数量
	 */
	public static final String X_TRTELIMIT_LIMIT = "X-RateLimit-Limit";

	/**
	 * 当前时间窗⼝口中还可以被请求的数量
	 */
	public static final String X_RARELIMIT_REMAINING = "X-RateLimit-Remaining";
	/**
	 * 距离速率限制的时间窗⼝口下次重置的秒数
	 */
	public static final String X_RARELIMIT_RESET = "X-RateLimit-Reset";

	/**
	 * @Autowired 代码实现了单例模式，spring就无法自动注入
	 */
	@Autowired
	private FlowControlConfig flowControlConfig;

	@Autowired
	CacheFlowControlInfo cacheFlowControlInfo;

	// 限流策略中的T时间内最大访问量
	private int threshold;
	// 配置文件中配置 限流策略中出现的时间间隔(周期))
	private long interval;
	// 令牌桶的令牌生成速率
	private double permitsPerSecond;
	// 计数
	private int count;
	// 计时
	private long time;
	// 每次请求提取当前时间做比较，时间到了清零计数器
	private long nowtime;
	// 分布式缓存的 时间key
	private String apptime;
	// 分布式缓存的计数key
	private String appcount;
	// 分布式缓存的锁名
	private String lockname;

	@Override
	protected boolean shouldNotFilterAsyncDispatch() {
		return false;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			flowControlRateLimiter(request, response, filterChain);
		} catch (Exception e) {
			ReflectionUtils.rethrowRuntimeException(e);
		}
	}

	/**
	 * 利用google 的guava工具包类RateLimiter 该类主要实现了令牌桶算法，通过匀速加入令牌来限制流量！
	 * 
	 * @param request     request
	 * @param response    response
	 * @param filterChain filterChain
	 * @throws ServletException     ServletException
	 * @throws IOException          IOException
	 * @throws InterruptedException InterruptedException
	 */
	public void flowControlRateLimiter(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException, InterruptedException {
		// 上分布式锁，上锁成功才进行流控处理
		nowtime = System.currentTimeMillis();
		lockname = flowControlConfig.getLockname();
		apptime = lockname + "time";
		appcount = lockname + "count";

		// 上分布式锁
//		jedis.lock(lockname, String.valueOf(nowtime), 2 * 1000);
		threshold = flowControlConfig.getThreshold();
		interval = flowControlConfig.getInterval();
		permitsPerSecond = (double) threshold / interval;
		// RateLimter默认使用了令牌桶的算法，下面是每秒放置permitsPerSecond个令牌
		RateLimiter rateLimiter = RateLimiter.create(permitsPerSecond, interval, TimeUnit.SECONDS);

		// 当key不存在的时候存基本kv到缓存
//		jedis.setnx(apptime, String.valueOf(nowtime));
//		cacheFlowControlInfo.setApptime(nowtime);
//		cacheFlowControlInfo.setAppcount(0);
//		jedis.setnx(appcount, "0");
		// 从缓存中获取count和time
		count = cacheFlowControlInfo.getAppcount();
		time = cacheFlowControlInfo.getApptime();

		if (time < nowtime) {
			count = 0;
			cacheFlowControlInfo.setAppcount(count);
//			jedis.set(appcount, Integer.toString(count));
		}
		// 判断是否能获取令牌，或者请求数已经超过当前的设置的threshold,返回TOO_MANY_REQUESTS
		if (!rateLimiter.tryAcquire() || count >= threshold) {
			logger.info("太多的请求了");
			HttpStatus httpStatus = HttpStatus.TOO_MANY_REQUESTS;
			response.setStatus(httpStatus.value());
			response.getWriter().append(httpStatus.getReasonPhrase());
			response.setHeader(X_TRTELIMIT_LIMIT, String.valueOf(threshold));
			response.setHeader(X_RARELIMIT_REMAINING, String.valueOf(threshold - count));
			response.setHeader(X_RARELIMIT_RESET, String.valueOf(time - nowtime));

		} else {
			if (count == 0) {
				time = nowtime + 1000 * interval;
				cacheFlowControlInfo.setApptime(time);
//				jedis.set(apptime, String.valueOf(time));
			}
			// 如果时间周期过了，那就可以将计数器清零
			if (time < nowtime) {
				cacheFlowControlInfo.setAppcount(0);
//				jedis.set(appcount, String.valueOf(count));
				time = nowtime + 1000 * interval;
				cacheFlowControlInfo.setApptime(time);
//				jedis.set(apptime, String.valueOf(time));
			}
			count++;
			cacheFlowControlInfo.setAppcount(count);
//			jedis.set(appcount, Integer.toString(count));

			logger.info("时间周期内的访问次数:" + cacheFlowControlInfo.getAppcount());

			response.setHeader(X_TRTELIMIT_LIMIT, String.valueOf(threshold));
			response.setHeader(X_RARELIMIT_REMAINING, String.valueOf(threshold - count));
			response.setHeader(X_RARELIMIT_RESET, String.valueOf(time - nowtime));

			logger.info("客户端在特定时间窗口中可以被允许使用的数量:" + threshold);
		}
		// 分布式锁解锁
//		jedis.unlock(lockname, String.valueOf(nowtime));
		// filterChain为过滤器链，表示执行完这个过滤器之后接着执行下一个过滤器
		filterChain.doFilter(request, response);
	}

}

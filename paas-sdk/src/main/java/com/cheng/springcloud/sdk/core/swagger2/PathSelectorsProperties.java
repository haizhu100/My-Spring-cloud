package com.cheng.springcloud.sdk.core.swagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author haizhu_cheng@163.com
 * @version Revision 1.0.0
 * @版权 版权所有 (c) 2018
 * @创建日期 2019年3月12日
 * @功能说明 应用资源路径选择
 */
public class PathSelectorsProperties {

	 /**
     * 隐藏路径匹配,example:[/user,/test/user] 隐藏swagger页面上/user及/user/**、/test/user及/test/user/下的所有路径
     */
    private List<String> excludePatterns = new ArrayList<>();
    /**
     * 显示路径匹配,example:[/user,/test/user] 显示swagger页面上/user及/user/**、/test/user及/test/user/下的所有路径
     */
    private List<String> includePatterns = new ArrayList<>();

    public boolean isEmpty() {
        return excludePatterns.isEmpty() && includePatterns.isEmpty();
    }

	public List<String> getExcludePatterns() {
		return excludePatterns;
	}

	public List<String> getIncludePatterns() {
		return includePatterns;
	}

	public void setExcludePatterns(List<String> excludePatterns) {
		this.excludePatterns = excludePatterns;
	}

	public void setIncludePatterns(List<String> includePatterns) {
		this.includePatterns = includePatterns;
	}
}

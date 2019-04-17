package com.cheng.springcloud.sdk.core.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.util.StreamUtils;

/**
 * @author haizhu_cheng@163.com
 * @version Revision 1.0.0
 * @版权 版权所有 (c) 2018
 * @创建日期 2019年03月05日
 * @功能说明 用于InputStream流和String互转
 */
public class InputStreamUtils {

	/**
	 * 将InputStream流转换成String
	 * 
	 * @param inputStream 被转化流
	 * @return String
	 */
	public static String inputStreamToString(InputStream inputStream) {
		String str = null;		
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int i;
			while ((i = inputStream.read()) != -1) {
				baos.write(i);
			}
			str = baos.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 将String转为InputStream
	 * 
	 * @param str 被转化的String
	 * @return InputStream
	 */
	public static InputStream stringToInputStream(String str) {
		if (str == null) {
			return StreamUtils.emptyInput();
		}
		InputStream is = new ByteArrayInputStream(str.getBytes());		
		return is;
	}
}

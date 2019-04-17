package com.cheng.springcloud.sdk.core.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author haizhu_cheng@163.com
 * @version Revision 1.0.0
 * @版权 版权所有 (c) 2018
 * @创建日期 2019年1月15日
 * @功能说明 实体对象转换成业务对象
 */
public class ReflectForeignKeyUtil {

	
	/**
	 * @description list的实体对象转业务对象
	 * @param object1 源实体对象
	 * @param classes 目标业务对象class
	 * @return 目标业务对象列表
	 */
	public static <T> List<T> transferObjectList(List<?> object1, Class<T> classes) {
		if (object1 == null || object1.isEmpty()) {
			return Collections.emptyList();
		}
		List<T> t = new ArrayList<>();
		for (int i = 0; i < object1.size(); i++) {
			try {
				t.add(i, transferObject(object1.get(i), classes));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return t;
	}

	/**
	 * @description 实体对象转业务对象
	 * @param object1 实体对象
	 * @param class2 目标对象class
	 * @return 返回目标业务对象
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static <T> T transferObject(Object object1, Class<T> class2)
			throws IllegalArgumentException, IllegalAccessException {
		Field[] fields2 = class2.getDeclaredFields();
		T object2 = null;
		try {
			object2 = class2.newInstance();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (Field f2 : fields2) {
			// 设置这些属性可以访问
			f2.setAccessible(true);
			String filedName2 = f2.getName();
			Field[] fields1 = object1.getClass().getDeclaredFields();

			for (Field f1 : fields1) {
				// 设置这些属性可以访问
				f1.setAccessible(true);
				String filedName1 = f1.getName();
				if (filedName1 =="serialVersionUID") {
					continue;
				}
				if (filedName1 == filedName2) {
					// isPrimitive 方法可以判断是不是基本数据类型
					if (f1.getType().isPrimitive() || isNormalType(f1.getType().toString())) {
						f2.set(object2, (Object) f1.get(object1));
					} else if (f1.getType().toString().endsWith("List")) {
						// list属性做转换
						Class<?> class1 = getListObject(f2);
						// 判断list里的对象是否是引用对象
						if (!class1.equals(f1.getType())) {
							List<?> listObject1 = (List<?>) f1.get(object1);
							f2.set(object2, transferObjectList(listObject1, class1));
						} else {
							f2.set(object2, (Object) f1.get(object1));
						}
					} else {
						// 普通对象做转换
						Object newObject1 = f1.get(object1);
						if (newObject1 == null) {
							continue;
						}
						transferObject(newObject1, f2.getType());
						f2.set(object2, transferObject(newObject1, f2.getType()));
					}
				}
			}
		}
		return object2;
	}

	/**
	 * @param string string
	 * @return 用于判断类的属性是普通常用属性
	 */
	public static boolean isNormalType(String string) {
		if (string.endsWith("String") || string.endsWith("Long") || string.endsWith("Double") || string.endsWith("Map")
				|| string.endsWith("Set") || string.endsWith("Date") || string.endsWith("BigDecimal")
				|| string.endsWith("Integer") || string.endsWith("Double") || string.endsWith("Boolean")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param f filed
	 * @return 通过反射获取list里面的泛型类型
	 */
	public static Class<?> getListObject(Field f) {
		Class<?> genericClazz = null;
		// 得到其Generic的类型
		Type genericType = f.getGenericType();
		// 如果是泛型参数的类型
		if (genericType instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) genericType;
			// 得到泛型里的class类型对象
			genericClazz = (Class<?>) pt.getActualTypeArguments()[0];
		}
		return genericClazz;
	}

}

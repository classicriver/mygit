package com.ptae.auth.common;

import java.util.Collection;
import java.util.Map;
import java.util.Random;

import org.springframework.util.StringUtils;


public class CommonUtils {
	/**
	 * 
	 * @param length  验证码长度
	 * @return
	 *@Description: 获取随机验证码
	 */
	public static String getRandomCode(int length) {
		String code ="";
		Random random=new Random();
		for(int i = 0; i<length; i++) {
			code = code + String.valueOf(random.nextInt(10));
		}
		return code;
	}
	/**
	 * 
	 * @param obj
	 * @return true : 空，false ： 不为空
	 * @Description: TODO 判空组件
	 */
	public static boolean isNullOrEmpty(Object obj) {
		if (obj == null)
			return true;

		if (obj instanceof CharSequence)
			return ((CharSequence) obj).length() == 0;

		if (obj instanceof Collection)
			return ((Collection<?>) obj).isEmpty();

		if (obj instanceof Map)
			return ((Map<?, ?>) obj).isEmpty();
		if (obj instanceof String) {
			return StringUtils.isEmpty(obj.toString());
		}
		if (obj instanceof Object[]) {
			Object[] object = (Object[]) obj;
			if (object.length == 0) {
				return true;
			}
			boolean empty = true;
			for (int i = 0; i < object.length; i++) {
				if (!isNullOrEmpty(object[i])) {
					empty = false;
					break;
				}
			}
			return empty;
		}
		return false;
	}
}

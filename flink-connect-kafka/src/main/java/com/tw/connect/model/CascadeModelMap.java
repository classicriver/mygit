package com.tw.connect.model;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;

import org.apache.avro.generic.GenericRecord;
import org.apache.flink.api.common.functions.MapFunction;

/**
 * 
 * @author xiesc
 * @TODO avro 转对象
 * @time 2019年1月18日
 * @version 1.0
 */
public class CascadeModelMap implements
		MapFunction<GenericRecord, CascadeModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public CascadeModel map(GenericRecord record) throws Exception {
		// TODO Auto-generated method stub
		CascadeModel cascade = new CascadeModel();
		Field[] fields = CascadeModel.class.getDeclaredFields();
		for (Field f : fields) {
			String name = f.getName();
			if ("esn".equals(name)) {
				invokeSetMethod(CascadeModel.class, cascade,
						fieldName2setMethodName(name), record.get(name)
								.toString(), String.class);
			} else if ("timestamps".equals(name)) {
				invokeSetMethod(CascadeModel.class, cascade,
						fieldName2setMethodName(name),
						new Long(record.get(name).toString()),
						long.class);
			} else if(("path".equals(name))){
				
			} else{
				invokeSetMethod(CascadeModel.class, cascade,
						fieldName2setMethodName(name), new BigDecimal(record
								.get(name).toString()),
						BigDecimal.class);
			}
		}
		return cascade;
	}
	/**
	 * 反射调用set方法
	 * @param clazz  pojoclass
	 * @param obj   pojo对象
	 * @param methodName set方法名
	 * @param args	set参数
	 * @param parameterTypes set参数类型
	 */
	private void invokeSetMethod(Class<?> clazz, Object obj, String methodName,
			Object args, Class<?> parameterTypes) {
		try {
			Method declaredMethod = clazz.getDeclaredMethod(methodName,
					parameterTypes);
			declaredMethod.invoke(obj, args);
		} catch (NoSuchMethodException | SecurityException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 字段名转set方法名
	 * @param fieldName
	 * @return
	 */
	private String fieldName2setMethodName(String fieldName) {
		if (Character.isUpperCase(fieldName.charAt(0)))
			return "set" + fieldName;
		else
			return new StringBuffer("set")
					.append(Character.toUpperCase(fieldName.charAt(0)))
					.append(fieldName.substring(1)).toString();
	}
}

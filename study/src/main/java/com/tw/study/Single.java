/**   
 */
package com.tw.study;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;

import sun.misc.Unsafe;
import sun.reflect.Reflection;

/**
 * @Description: TODO
 * @author xiesc
 * @date 2017年12月28日
 * @version V1.0  
 */
public class Single {
	private long value;
	private String s;
	public static long sizeOf(Object o) {
		Unsafe u = getUnsafe();
		HashSet<Field> fields = new HashSet<Field>();
		Class c = o.getClass();
		while (c != Object.class) {
			for (Field f : c.getDeclaredFields()) {
				if ((f.getModifiers() & Modifier.STATIC) == 0) {
					fields.add(f);
				}
			}
			c = c.getSuperclass();
		}

		// get offset
		long maxSize = 0;
		for (Field f : fields) {
			long offset = u.objectFieldOffset(f);
			if (offset > maxSize) {
				maxSize = offset;
			}
		}

		return ((maxSize / 8) + 1) * 8; // padding
	}

	public void test() {
		Field f;
		try {
			f = Single.class.getDeclaredField("s");
			Unsafe u = getUnsafe();
			long offset = u.objectFieldOffset(f);
			//u.compareAndSwapLong(this, offset, value, 2L);
			u.compareAndSwapObject(this, offset, s, "123");
			System.out.println(s);
		} catch (NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static Unsafe getUnsafe() {
		// TODO Auto-generated method stub
		Field f;
		Unsafe unsafe = null;
		try {
			f = Unsafe.class.getDeclaredField("theUnsafe");
			f.setAccessible(true);
			unsafe = (Unsafe) f.get(null);
		} catch (NoSuchFieldException | SecurityException
				| IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return unsafe;
	}

	public static void main(String[] args) {
		/*String s = new String(
				"123456123123112313213213131231231311243134124214124124124141241241");
		long sizeOf = Single.sizeOf(s);
		System.out.println(sizeOf);*/
		new Single().test();
	}
}

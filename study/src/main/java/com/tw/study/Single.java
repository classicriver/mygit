/**   
*/ 
package com.tw.study;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;

import sun.misc.Unsafe;

/**
 * @Description: TODO 双重检验锁单例模式
* @author  xiesc
* @date 2017年12月28日 
* @version V1.0   
 */
public class Single {
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

	    return ((maxSize/8) + 1) * 8;   // padding
	}

	private static Unsafe getUnsafe() {
		// TODO Auto-generated method stub
		Field f;
		Unsafe unsafe = null;
		try {
			f = Unsafe.class.getDeclaredField("theUnsafe");
			f.setAccessible(true);
			unsafe = (Unsafe) f.get(null);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return unsafe;
	}
	
	public static void main(String[] args) {
		String s = new String("123");
		long sizeOf = Single.sizeOf(s);
		System.out.println(sizeOf);
	}
}

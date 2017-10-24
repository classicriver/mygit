package com.ptae.auth.common;

import java.security.MessageDigest;

/**
 * @Description: TODO()
 * @author xiesc
 * @date 2017年10月16日
 * @version V1.0  
 */
public class MD5Utils {
	/**
	 * 
	 * @param inStr 
	 * @return
	 * @Description: TODO 字符床转为MD5码
	 */
	public static String MD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
<<<<<<< HEAD
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];

		byte[] md5Bytes = md5.digest(byteArray);

		StringBuffer hexValue = new StringBuffer();

		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}

		return hexValue.toString();
	}

	/**
	 * 
	 * @param inStr 需要加密的字符串
	 * @return
	 * @Description: TODO 字符串直接加密
	 */
	public static String MD5encrypt(String inStr) {
		String md5String = MD5(inStr);
		char[] a = md5String.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't');
		}
		String s = new String(a);
		return s;
	}

	/**
	 * 
	 * @param md5Str 需要加密的md5码
	 * @return
	 * @Description: TODO MD5码加密
	 */
	public static String encrypt(String md5Str) {
		char[] a = md5Str.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't');
		}
		String s = new String(a);
		return s;
	}

	/**
	 * 
	 * @param inStr  加密后的字符串
	 * @return
	 * @Description: TODO 解密为md5码
	 */
	public static String decode(String inStr) {
		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't');
		}
		String k = new String(a);
		return k;
	}
	/**
	 * 
	 * @param code md5码
	 * @return
	 * @Description: TODO 验证md5码是否相同
	 */
	public static boolean verify(String code) {
		//String str = UrlUtil.getURLDecoderString(code);
		if(!CommonUtils.isNullOrEmpty(code)){
			if(MD5("PTAE").equals(code)){
				return true;
			}
		}
		return false;
	}

=======
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];

		byte[] md5Bytes = md5.digest(byteArray);

		StringBuffer hexValue = new StringBuffer();

		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}

		return hexValue.toString();
	}

	/**
	 * 
	 * @param inStr 需要加密的字符串
	 * @return
	 * @Description: TODO 字符串直接加密
	 */
	public static String MD5encrypt(String inStr) {
		String md5String = MD5(inStr);
		char[] a = md5String.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't');
		}
		String s = new String(a);
		return s;
	}

	/**
	 * 
	 * @param md5Str 需要加密的md5码
	 * @return
	 * @Description: TODO MD5码加密
	 */
	public static String encrypt(String md5Str) {
		char[] a = md5Str.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't');
		}
		String s = new String(a);
		return s;
	}

	/**
	 * 
	 * @param inStr  加密后的字符串
	 * @return
	 * @Description: TODO 解密为md5码
	 */
	public static String decode(String inStr) {
		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't');
		}
		String k = new String(a);
		return k;
	}
	/**
	 * 
	 * @param code md5码
	 * @return
	 * @Description: TODO 验证md5码是否相同
	 */
	public static boolean verify(String code) {
		if(!CommonUtils.isNullOrEmpty(code)){
			if(MD5("PTAE").equals(decode(code))){
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		System.out.println(MD5Utils.MD5("PTAE"));
		System.out.println(MD5Utils.encrypt(MD5Utils.MD5("PTAE")));
	}
>>>>>>> branch 'master' of https://github.com/classicriver/mygit
}

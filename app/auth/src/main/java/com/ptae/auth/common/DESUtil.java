/**
*/
package com.ptae.auth.common;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;

/**
 * @Description: TODO(des对称加密)
 * @author xiesc
 * @date 2017年10月20日
 * @version V1.0  
 */
public class DESUtil {
	//算法名称
	public static final String KEY_ALGORITHM = "DES";
	//算法名称/加密模式/填充方式
	//DES共有四种工作模式-->>ECB:电子密码本模式、CBC：加密分组链接模式、CFB：加密反馈模式、OFB：输出反馈模式
	public static final String CIPHER_ALGORITHM = "DES/ECB/NoPadding";
	//密钥
	public static final String KEY = "P1T2A3E412345678";
	//需要加密的内容
	public static final String SOURCE = "PTAE1234";
	/**
	 * 
	 * @param keyStr
	 * @return
	 * @throws Exception
	 * @Description: TODO
	 */
	private static SecretKey keyGenerator(String keyStr) throws Exception {
		byte input[] = HexString2Bytes(keyStr);
		DESKeySpec desKey = new DESKeySpec(input);
		//创建一个密匙工厂，然后用它把DESKeySpec转换成
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey securekey = keyFactory.generateSecret(desKey);
		return securekey;
	}

	private static int parse(char c) {
		if (c >= 'a')
			return (c - 'a' + 10) & 0x0f;
		if (c >= 'A')
			return (c - 'A' + 10) & 0x0f;
		return (c - '0') & 0x0f;
	}

	//从十六进制字符串到字节数组转换
	public static byte[] HexString2Bytes(String hexstr) {
		byte[] b = new byte[hexstr.length() / 2];
		int j = 0;
		for (int i = 0; i < b.length; i++) {
			char c0 = hexstr.charAt(j++);
			char c1 = hexstr.charAt(j++);
			b[i] = (byte) ((parse(c0) << 4) | parse(c1));
		}
		return b;
	}

	/**
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 * @Description: TODO
	 */
	public static String encrypt(String data) throws Exception {
		Key deskey = keyGenerator(KEY);
		//实例化Cipher对象，它用于完成实际的加密操作
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		SecureRandom random = new SecureRandom();
		//初始化Cipher对象,设置为加密模式
		cipher.init(Cipher.ENCRYPT_MODE, deskey, random);
		byte[] results = cipher.doFinal(data.getBytes());
		//执行加密操作。加密后的结果通常都会用Base64编码进行传输
		return Base64.encodeBase64String(results);
	}

	/**
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 * @Description: TODO
	 */
	public static String decrypt(String data) throws Exception {
		Key deskey = keyGenerator(KEY);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		//初始化Cipher对象，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, deskey);
		//执行解密操作
		return new String(cipher.doFinal(Base64.decodeBase64(data)));
	}
	
	//获取加密后的密文
	public static String getEncrypt(){
		try {
			return encrypt(SOURCE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public static boolean verify(String ciphertext){
		if(DESUtil.getEncrypt().equals(ciphertext)){
			return true;
		}
		return false;
		
	}
}

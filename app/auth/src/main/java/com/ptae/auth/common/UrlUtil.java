/**
*/
package com.ptae.auth.common;

import java.io.UnsupportedEncodingException;

/**
 * @Description: TODO()
 * @author  xiesc
 * @date 2017年10月19日 
 * @version V1.0  
 */
public class UrlUtil {
	 private final static String ENCODE = "UTF-8"; 
	    /**
	     * URL 解码
	     *
	     * @return String
	     * @author lifq
	     * @date 2015-3-17 下午04:09:51
	     */
	    public static String getURLDecoderString(String str) {
	        String result = "";
	        if (null == str) {
	            return "";
	        }
	        try {
	            result = java.net.URLDecoder.decode(str, ENCODE);
	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        }
	        return result;
	    }
	    /**
	     * URL 转码
	     *
	     * @return String
	     * @author lifq
	     * @date 2015-3-17 下午04:10:28
	     */
	    public static String getURLEncoderString(String str) {
	        String result = "";
	        if (null == str) {
	            return "";
	        }
	        try {
	            result = java.net.URLEncoder.encode(str, ENCODE);
	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        }
	        return result;
	    }


}

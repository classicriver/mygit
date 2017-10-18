package com.ptae.auth.common;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author xiesc
 *<br> 使用jjwt生成和解析token
 */
public class JJWTUtils {
	
	private static final String BINARY_CODE = "PTAE";
	/**
	 * 
	 * @param subject 用户账号
	 * @param ttlMillis 过期时间
	 * @return
	 * @Description: TODO 通过用户账号生成token
	 */
	public static String getToken(String subject, long ttlMillis) {
		//加密方式
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		 
		//获取加密的key
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(BINARY_CODE);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
		//设置 JWT Claims
		JwtBuilder builder = Jwts.builder()
		                                .setIssuedAt(now)
		                                .setSubject(subject)
		                                .signWith(signatureAlgorithm, signingKey);
		 
		//如果指定了过期时间，添加过期时间
		if (ttlMillis >= 0) {
		    long expMillis = nowMillis + ttlMillis;
		    Date exp = new Date(expMillis);
		    builder.setExpiration(exp);
		}
		//Builds the JWT and serializes it to a compact, URL-safe string
		return builder.compact();

	}
	/**
	 * 
	 * @param token 
	 * @return
	 * @Description: 获取用户的账号
	 */
	public static String getSubject(String token) {
		return Jwts.parser()        
				   .setSigningKey(DatatypeConverter.parseBase64Binary(BINARY_CODE))
				   .parseClaimsJws(token).getBody().getSubject();
	}
	
	/** 
	 * @param token
	 * @return 
	 * @Description: TODO 获取token的签名
	 */
	public static String getSignature(String token) {

		return Jwts.parser()        
		   .setSigningKey(DatatypeConverter.parseBase64Binary(BINARY_CODE))
		   .parseClaimsJws(token).getSignature();
	}
	
}

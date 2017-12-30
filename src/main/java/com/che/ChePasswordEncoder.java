package com.che;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import sun.misc.BASE64Encoder;

public class ChePasswordEncoder implements PasswordEncoder {
	// 盐值（加密混淆）
    private final static String slat = "diasj29er2ur734tuei89u34efdfi30q7u5834tdphf056=-251758";
    
	/**
	 * 利用MD5进行加密
	 * 
	 * @param str 待加密的字符串
	 * @return 加密后的字符串
	 * @throws NoSuchAlgorithmException 没有这种产生消息摘要的算法
	 * @throws UnsupportedEncodingException
	 */
	@Override
	public String encode(CharSequence rawPassword) {
		if (rawPassword == null) {
			return null;
		}
		String newstr = null;
		rawPassword = rawPassword + "/" + slat;
		// 确定计算方法
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
			BASE64Encoder base64en = new BASE64Encoder();
			// 加密后的字符串
			newstr = base64en.encode(md5.digest(rawPassword.toString().getBytes("utf-8")));
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		System.out.println("==========================="+newstr);
		return newstr;
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		String encodeedRawPassword = StringUtils.isNotBlank(rawPassword) ? this.encode(rawPassword.toString()) : null;
		boolean matched = StringUtils.equals(encodeedRawPassword, encodedPassword);
		return matched;
	}
}

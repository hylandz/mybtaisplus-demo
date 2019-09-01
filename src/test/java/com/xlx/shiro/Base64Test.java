package com.xlx.shiro;

import org.apache.shiro.codec.Base64;
import org.junit.Test;
import org.springframework.util.Base64Utils;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * shiro解/编码
 *
 * @author xielx on 2019/7/23
 */
public class Base64Test {


	@Test
	public void testBase64(){
		String str = "springbootshiro";//c3ByaW5nYm9vdHNoaXJv
		byte[] strByte;
		try {
			strByte = str.getBytes("UTF-8");
			System.out.println(new String(Base64.encode(strByte)));
			System.out.println(Base64.decodeToString("c3ByaW5nYm9vdHNoaXJv"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}

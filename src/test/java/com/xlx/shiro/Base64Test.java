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
	
	@Test
	public void bitCalculate(){
		int a = 10 >> 1;//a=10/2^1=5
		int b = a++;//b=5,a=6
		int c = ++a;//c=7,a=7
		int d = b + a++;//d=5+7=12,a=8
		System.out.println("a=" + a + ",b=" + b + ",c=" + c + ",d=" + d);
		int i = 4;
		int j = i++;
		System.out.println(i + "," + j);
	}
	
	
	@Test
	public void testPhoneRepace(){
		String  phone = "17350852927";
		String replace = "****";
		StringBuilder builder = new StringBuilder(phone);
		builder.replace(3,7,replace);
		//phone = phone.substring(0,3) + replace + phone.substring(7);
		System.out.println(builder.toString());// 173****2927
	}
}

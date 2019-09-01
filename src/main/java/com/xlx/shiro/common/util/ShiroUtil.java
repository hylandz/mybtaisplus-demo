package com.xlx.shiro.common.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;

/**
 * shiro的密码加密
 *
 * @author xielx on 2019/7/22
 */
public class ShiroUtil {

	//private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

	//@Value("${shiro.crypto.algorithmName}")
	private static final String algorithmName = "md5";
	//@Value("${shiro.crypto.hashIterations}")
	private static final Integer hashIterations = 2;


	/**
	 * 生成32位随机数
	 */
	public static String getHexRandomNumber(){
		return new SecureRandomNumberGenerator().nextBytes().toHex();
	}

	/**
	 * 密码加密
	 * @param pwd 明文密码
	 * @param credentialsSalt 盐(userName + salt)
	 * @return 16进制编码存储密码
	 */
	public static String encryptPassword(String pwd,String credentialsSalt){
		SimpleHash simpleHash = new SimpleHash(algorithmName,pwd, ByteSource.Util.bytes(credentialsSalt),hashIterations);
		return simpleHash.toHex();
	}


	/**
	 * 获取Subject
	 * @return obj
	 */
	public static Subject getSubject(){
		return SecurityUtils.getSubject();
	}
	public static void main(String[] args) {
		String num = getHexRandomNumber();
		String en = encryptPassword("G0009264","G0009264dc626f3f41d0140b21cd9be2038dfc6a");
		System.out.println(num);
		System.out.println(en);
	}
}

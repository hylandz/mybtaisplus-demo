package com.xlx.shiro.common.util;

import com.xlx.shiro.system.dto.ResultDTO;
import com.xlx.shiro.system.service.UserService;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * jwt工具类
 *
 * @author xielx on 2019/7/14
 */
@Slf4j
@Component
public class JwtUtils {


  private static UserService userService;

  @Autowired
  public JwtUtils(UserService userService){
    JwtUtils.userService = userService;
  }

  public static final long EXPIRATION_TIME = 10*60*1000L; // 10 min

  static final String SECRET = "6KW/57qi5p+/54KS6bih6JuL";//西红柿炒鸡蛋
  static final String TOKEN_PREFIX = "Bearer_";
  static final String HEADER_STRING = "Authorization";


  /**
   * 创建JWT/token(有效时间:登录时间+10min)
   * header:
   * 负载均衡:hashMap
   * 签名:HS256加密+SECRET密钥
   * @param userName 登录用户
   * @param generateTime 登录时间也是token生成时间
   * @return.
   */
  public static String generateToken(String userName, Date generateTime){

    SecretKey secretKey = createSecretKey();
    // 填写Header
    Map<String,Object> header = new HashMap<>();
    header.put("alg","HS256");
    header.put("typ","JWT");

    //payload的私有/自定义声明
    Map<String,Object> privateClaim = new HashMap<>();
    privateClaim.put("username",userName);
    privateClaim.put("generateTime", DateUtils.formatString(generateTime));

    log.info("有效时长:[{}]", DateUtils.formatString(new Date(generateTime.getTime() + EXPIRATION_TIME)));
    String jwt = Jwts.builder()
            .setHeader(header)
            .setClaims(privateClaim)
            .setIssuedAt(generateTime)
            .setExpiration(new Date(generateTime.getTime() + EXPIRATION_TIME))
            .setId(UUID.randomUUID().toString())
            .signWith(SignatureAlgorithm.HS256,secretKey)
            .compact();
    return TOKEN_PREFIX + jwt;

  }


  /**
   * 校验token
   * 过期token的如何处理????
   * @param token 返回的jwt/token
   * @return .
   */
  public static ResultDTO validateToken(String token){
    if (token != null) {
      // 解析token
      try {
        long expireTime  = Jwts.parser()
                .setSigningKey(createSecretKey())
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody()
                .getExpiration()
                .getTime();
        long diff = expireTime - System.currentTimeMillis();


        return ResultDTO.success("token未过期",diff/60*1000);
      }catch (SignatureException | MalformedJwtException e) {
        // 签证更改,即token错误
        return ResultDTO.failed("无效的token");
      } catch (ExpiredJwtException e) {
        log.error("========token过期============");
        // 过期token处理
        return ResultDTO.failed(e.getMessage());
      }
    }

    return ResultDTO.failed("token参数为NULL");
  }





  /**
   * 获取jwt的payload部分
   * @param token .
   * @return .
   */
  public static Claims getClaim(String token){
    SecretKey secretKey = createSecretKey();
      return Jwts.parser()
              .setSigningKey(secretKey)
              .parseClaimsJws(TOKEN_PREFIX)
              .getBody();
    }

  /**
   * 创建SECRET
   * @return .
   */
  private static SecretKey createSecretKey(){
    byte[] encodeKey = Base64.decodeBase64(SECRET);//编码过的SECRET
    SecretKey secretKey = new SecretKeySpec(encodeKey,0,encodeKey.length,"AES");
    return secretKey;
  }



}

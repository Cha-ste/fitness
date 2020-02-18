package com.ocean.utils;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.ocean.redis.RedisService;
import com.ocean.redis.UserPrefix;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Token工具类
 */
@Component
public class TokenUtils {
    private static final String  SECRET = "shuXieChuanQiCongWoKaiShi2019999";
    private static final String  VALID = "valid";
    private static final String  EXPIRED = "expired";
    private static final String  INVALID = "invalid";

    @Autowired
    private static RedisService redisService;

    private static final JWSHeader header=new JWSHeader(JWSAlgorithm.HS256, JOSEObjectType.JWT, null, null, null, null, null, null, null, null, null, null, null);

    /**
     * 生成token，该方法只在用户登录成功后调用
     *
     * @param payload Map集合，可以存储用户id，token生成时间，token过期时间等自定义字段
     * @return token字符串,若失败则返回null
     */
    public static String createToken(Map<String, Object> payload) {
        String tokenString=null;
        // 创建一个 JWS object
        JWSObject jwsObject = new JWSObject(header, new Payload(new JSONObject(payload)));
        try {
            // 将jwsObject 进行HMAC签名
            jwsObject.sign(new MACSigner(SECRET));

            tokenString=jwsObject.serialize();
        } catch (JOSEException e) {
            System.err.println("签名失败:" + e.getMessage());
            e.printStackTrace();
        }
        return tokenString;
    }

//    public static String genToken() {
//
//    }


    /**
     * 验证token
     */
    public static Boolean validToken(String token) {
        String value = redisService.get(UserPrefix.getByToken, token, String.class);
        return StringUtils.isEmpty(value);
    }

    /**
     * 校验token是否合法
     *
     * @param token 字符串
     * @return token状态
     */
    /*public static boolean validate(String token) {
        Map<String, Object> payload = validToken(token);
        String state = (String)payload.get("state");

        switch (state) {
            case VALID:
                return true;
            case EXPIRED:
                return false;
            default:
                return false;
        }
    }

    private static Map<String, Object> validToken(String token) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            JWSObject jwsObject = JWSObject.parse(token);
            Payload payload = jwsObject.getPayload();
            JWSVerifier verifier = new MACVerifier(SECRET);

            if (jwsObject.verify(verifier)) {
                JSONObject jsonOBj = payload.toJSONObject();
                // token校验成功（此时没有校验是否过期）
                resultMap.put("state", VALID);
                // 若payload包含ext字段，则校验是否过期
                if (jsonOBj.containsKey("ext")) {
                    long extTime = Long.valueOf(jsonOBj.get("ext").toString());
                    long curTime = new Date().getTime();
                    // 过期了
                    if (curTime > extTime) {
                        resultMap.clear();
                        resultMap.put("state", EXPIRED);
                    }
                }
                resultMap.put("data", jsonOBj);

            } else {
                // 校验失败
                resultMap.put("state", INVALID);
            }

        } catch (Exception e) {
            //e.printStackTrace();
            // token格式不合法导致的异常
            resultMap.clear();
            resultMap.put("state", INVALID);
        }
        return resultMap;
    }*/

    public static boolean updateExp(String token) {
        String redisToken = JedisUtils.getString(token);
        if (redisToken == null) return false;
        long expiredTime = Long.parseLong(redisToken);
        return expiredTime <= System.currentTimeMillis();
    }

}

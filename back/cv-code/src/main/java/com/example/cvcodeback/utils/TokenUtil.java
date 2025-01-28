package com.example.cvcodeback.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtil {
    @Value("${token.secretKey}")
    private String secretKey;
    /**
     * 加密token.
     */
    public String getToken(String userId, String userName, String nickName) {
        String token = JWT
                .create()
                .withClaim("userId" ,userId)
                .withClaim("userName" ,userName)
                .withClaim("nickName", nickName)
                .sign(Algorithm.HMAC256(Base64.getEncoder().encode(secretKey.getBytes())));
        return token;
    }
    /**
     * 解析token.
     */
    public Map<String, String> parseToken(String token) {
        HashMap<String, String> map = new HashMap<String, String>();
        DecodedJWT decodedjwt = JWT.require(Algorithm.HMAC256(Base64.getEncoder().encode(secretKey.getBytes())))
                .build().verify(token);
        Claim userId = decodedjwt.getClaim("userId");
        Claim userName = decodedjwt.getClaim("userName");
        Claim nickName = decodedjwt.getClaim("nickName");
        map.put("userId", userId.asString());
        map.put("userName", userName.asString());
        map.put("nickName", nickName.asString());
        return map;
    }
}

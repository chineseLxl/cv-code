package com.example.cvcodeback.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

@Component
public class PasswordMd5 {
    @Value("${password.secretKey}")
    String secretKey;
    public String encode(String pwd) {
        String hashedPwd = DigestUtils.md5DigestAsHex((pwd + secretKey).getBytes());
        return hashedPwd;
    }

}

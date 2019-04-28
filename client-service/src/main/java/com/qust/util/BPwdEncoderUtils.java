package com.qust.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class BPwdEncoderUtils {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
    *  用BCryptPasswordEncoder
    *@Param [password]
    *@Return java.lang.String
    *@Author 孙良玉
    */
    public static String  BCryptPassword(String password){
        return encoder.encode(password);
    }

    /**
     *
     * @param rawPassword 原始密码
     * @param encodedPassword 加密后的密码
     * @return
     * @Author 孙良玉
     */
    public static boolean matches(CharSequence rawPassword, String encodedPassword){
        return encoder.matches(rawPassword,encodedPassword);
    }

}

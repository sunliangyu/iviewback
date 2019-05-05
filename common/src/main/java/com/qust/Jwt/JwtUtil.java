package com.qust.Jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    //24小时
    public static final long EXIST_TIME = 23 *60 * 60 * 1000 ;
    public static final String SECURITY = "woshitokenmiyao123666lalala";
    public static String getToken ( Long userid  , Long restaurant) {
        try{
            Date data = new Date(System.currentTimeMillis() + EXIST_TIME);
            // 私钥 加密算法
            Algorithm algorithm = Algorithm.HMAC256(SECURITY);
            Map<String,Object> header = new HashMap<>(2);
            header.put("typ","JWT");
            header.put("alg","HS256");
            return JWT.create().withHeader(header)
                    .withClaim("userid",userid)
                    .withClaim("restaurant",restaurant)
                    .withExpiresAt(data)
                    .sign(algorithm);
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, Claim> parserToken(String token){
        try {
            Map<String, Claim> result;
            Algorithm algorithm = Algorithm.HMAC256(SECURITY);
            JWTVerifier verifier = JWT.require(algorithm).build();
            result = verifier.verify(token).getClaims();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Map verify (String token) throws Exception {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECURITY);
            JWTVerifier verifier = JWT.require(algorithm).build();
            return verifier.verify(token).getClaims();
        } catch (Exception e) {
            return null;
        }
    }



}

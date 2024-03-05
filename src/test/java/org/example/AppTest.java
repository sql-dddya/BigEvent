package org.example;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AppTest{

    @Test
    public void tokenGen(){
        Map<String, Object> claims = new HashMap<>();
        claims.put("id","1");
        claims.put("username", "张三");

         String token = JWT.create()
                .withClaim("user", claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 60))
                .sign(Algorithm.HMAC256("itheima"));
        System.out.println(token);
    }

    @Test
    public void paraseToken(){
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
                ".eyJ1c2VyIjp7ImlkIjoiMSIsInVzZXJuYW1lIjoi5byg5LiJIn0sImV4cCI6MTcwOTY3MzU2NH0" +
                ".r5buLsg5C79IxIUs5j9DKl-nQqi1aujuyb-KZOc6VPQ";

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("itheima")).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        Map<String, Claim> claims =  decodedJWT.getClaims();
        System.out.println(claims.get("user"));
    }
}

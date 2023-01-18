package com.example.demo.security;

import com.example.demo.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class TokenProvider {
    private static final String SECRET_KEY = "d2fka30DdkjfaDJjklDkopwKlHwqodo";

    public String create(UserEntity userEntity){
        // 기한 : 지금으로부터 1일로 설정
        Date expiryDate = Date.from(
                Instant.now()
                        .plus(1, ChronoUnit.DAYS));

        //JSON Token 생성
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY) //header에 들어갈 내용
                .setSubject(userEntity.getId()) //payload에 들어갈 내용
                .setIssuer("demo app") //iss
                .setIssuedAt(new Date()) //iat
                .setExpiration(expiryDate) //exp
                .compact();
    }

    public String validateAndGetUserId(String token){
        //parseClaimsJws : Base 64로 디코딩 및 파싱
            // 즉, 헤더와 페이로드를 setSigningKey로 넘어온 시크릿을 이용해 서명 후, token의 서명과 비교
        // 위조되지 않았다면 페이로드(Claims)리턴, 위조라면 예외를 날림
        // 그 중 우리는 userId가 필요하므로 getBody를 부른다
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}

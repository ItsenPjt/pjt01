package com.newcen.newcen.common.config.security;


//토큰을 발급하고 서명위조를 확인해주는 객체

import com.newcen.newcen.common.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@Slf4j
public class TokenProvider {

    //토큰 서명에 사용할 불변성을 가진 비밀 키
    private static final String SECRET_KEY = "Q4NSl604sgyHJj1qwEkR3ycUeR4uUAt7WJraD7EN3O9DVM4yyYuHxMEbSF4XXyYJkal13eqgB0F7Bq4H";

    //토큰 발급 메서드
    public String createToken(UserEntity userEntity){
        //만료시간 설정
        Date expiryDate = Date.from(
                Instant.now()
                        .plus(1, ChronoUnit.DAYS)
        );

        //토큰생성

        return Jwts.builder()
                //header에 들어갈 서명
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()),
                        SignatureAlgorithm.HS512
                )
                .setSubject(userEntity.getUserId()) //sub:토큰 식별자
                .setIssuer("newcen app")  //iss:발급자정보
                .setIssuedAt(new Date()) //At: 토큰 발급시간
                .setExpiration(expiryDate) //exp: 토큰 만료시간
                .compact();

    }
    //
    /*클라이언트가 보낸 토큰을 디코딩 및 파싱해서 토큰 위조여부 확인
    * @param token - 클라이언트가 전송한 인코딩된 토큰
    * @return 토큰에서 subject(userId)를 꺼내서 반환
    * 유저 식별자 반환
    * */
    public String validateAndGetUserId(String token){

        Claims claims = Jwts.parserBuilder()

                //토큰 발급자의 발급당시 서명을 넣어줌
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                //토큰을 디코딩 서명기록을 파싱
                //클라이언트 토큰의 서명과 서버발급당시 서명을 비교
                //위조되지 않았다면 'body'에 페이로드(Claims)를 리턴
                //위조되었으면 예외를 발생시킴
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

}

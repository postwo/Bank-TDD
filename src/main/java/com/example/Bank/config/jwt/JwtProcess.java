package com.example.Bank.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.Bank.config.auth.LoginUser;
import com.example.Bank.domain.user.User;
import com.example.Bank.domain.user.UserEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class JwtProcess {

    // 토큰 생성 == 토큰은 정보를 다 열어서 확인이 가능하기 때문에 id나 role 정보만 담는다
    public static String create(LoginUser loginUser) {
        String jwtToken = JWT.create()
                .withSubject("bank") //토큰 제목 마음대로 작성해도 된다
                //System.currentTimeMillis() =현재시간 ,JwtVO.EXPIRATION_TIME= 7일
                // 이거는 쉽게 말해서 이토큰이 만들어지고 7일동안 유효합니다라는 뜻이다
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtVO.EXPIRATION_TIME))//만료시간
                .withClaim("id",loginUser.getUser().getId())
                .withClaim("role",loginUser.getUser().getRole().name()) //getRole이 userenum 타입이여서 스트링으로 캐스팅을 해줘야 한다 getRole().name())== getRole()+""
                .sign(Algorithm.HMAC512(JwtVO.SECRET)); // 토큰 생성
        return JwtVO.TOKEN_PREFIX+jwtToken;
    }

    // 토큰 검증(return 되는 LoginUser 객체를 강제로 시큐리티 세션에 직접 주입할 예정) = 강제로그인
    public static LoginUser verify(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(JwtVO.SECRET)).build().verify(token);
        Long id = decodedJWT.getClaim("id").asLong();
        String role = decodedJWT.getClaim("role").asString();
        User user = User.builder().id(id).role(UserEnum.valueOf(role)).build();
        LoginUser loginUser = new LoginUser(user);
        return loginUser;
    }
}

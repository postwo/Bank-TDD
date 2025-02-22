package com.example.Bank.config.jwt;

import com.example.Bank.config.auth.LoginUser;
import com.example.Bank.dto.user.UserReqDto;
import com.example.Bank.dto.user.UserRespDto;
import com.example.Bank.util.CustomResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        /*본적으로 UsernamePasswordAuthenticationFilter는 "/login" 경로에서 동작하도록 설정되어 있습니다.하지만
        setFilterProcessesUrl("/api/login")을 사용하면 로그인 요청을 받을 URL을 원하는 경로로 변경할 수 있습니다.*/
        setFilterProcessesUrl("/api/login");
        this.authenticationManager = authenticationManager;
    }

    // post의 /api/login 이 들어오면 토큰생성
    // 로그인 하면 request 에 json데이터가 담겨 있다
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        log.debug("디버그 : JwtAuthenticationFilter attemptAuthentication()");

        try {
            ObjectMapper om = new ObjectMapper(); // Java 객체를 JSON으로 변환하거나 JSON을 Java 객체로 변환
            UserReqDto.LoginReqDto loginReqDto = om.readValue(request.getInputStream(), UserReqDto.LoginReqDto.class);

            //강제로그인
            //강제로 로그인을 진행하는 이유는 JWT를 쓴다 하더라도, 컨트롤러 진입을 하면 시큐리티의 권한체크,인증체크의 도움을 받을 수 있게 세션을 만든다
            //이 세션의 유효기간은 request하고 ,response하면 끝
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginReqDto.getUsername(),
                    loginReqDto.getPassword());

            // UserDetailsService 호출 코드
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            return authentication;
        } catch (Exception e) {
            // unsuccessfulAuthentication 호출함
            throw new InternalAuthenticationServiceException(e.getMessage());
        }
    }

    // 로그인 실패
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        CustomResponseUtil.unAuthentication(response, "로그인실패");
    }

    //return authentication 잘 작도동하면 successfulAuthentication 메서드 호출 된다
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        log.debug("디버그 : JwtAuthenticationFilter successfulAuthentication()"); // 예가 호출이 되었다는거는 로그인이 되었다는의미이다 세션이 만들어졌다

        // 1. 세션에 있는 UserDetails 가져오기
        LoginUser loginUser = (LoginUser) authResult.getPrincipal();

        // 2. 세션값으로 토큰 생성
        String jwtToken = JwtProcess.create(loginUser);

        // 3. 토큰을 헤더에 담기 = 바디값이다
        response.addHeader(JwtVO.HEADER, jwtToken);

        // 4. 토큰 담아서 성공 응답하기
        UserRespDto.LoginRespDto loginRespDto = new UserRespDto.LoginRespDto(loginUser.getUser());
        CustomResponseUtil.success(response, loginRespDto);
    }

}

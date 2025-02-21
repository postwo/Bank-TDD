package com.example.Bank.util;

import com.example.Bank.dto.ResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomResponseUtil {

    //여기서 Object타입으로 받는이유는 예를 들어, 로그인 성공 시에는 UserRespDto.LoginRespDto를 반환하지만,
    //회원가입 성공 시에는 UserRespDto.SignUpRespDto를 반환할 수도 있기때문이다
    public static void success(HttpServletResponse response, Object dto) {
        try {
            ObjectMapper om = new ObjectMapper();
            ResponseDto<?> responseDto = new ResponseDto<>(1, "로그인성공", dto);
            String responseBody = om.writeValueAsString(responseDto);
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(200);
            response.getWriter().println(responseBody); // 예쁘게 메시지를 포장하는 공통적인 응답 DTO를 만들어보자!!
        } catch (Exception e) {
            log.error("서버 파싱 에러");
        }
    }

    public static void unAuthentication(HttpServletResponse response,String msg){
        try {
            ObjectMapper om = new ObjectMapper();
            ResponseDto<?> responseDto = new ResponseDto<>(-1,msg,null);
            String responseBody = om.writeValueAsString(responseDto);
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); //401
            response.getWriter().println(responseBody); //포스트맨 or 웹 으로 localhost:8081/api/s/hello 이 경로를 찍어보면 error가 찍힌다.
        }catch (Exception e){
            log.error("서버 파싱 에러");
        }

    }


}

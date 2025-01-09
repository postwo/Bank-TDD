package com.example.Bank.util;

import com.example.Bank.dto.ResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomResponseUtil {

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

    //지금은 굳이 이렇게까지 할필요 없다
//    public static void unAuthorization(HttpServletResponse response,String msg){
//        try {
//            ObjectMapper om = new ObjectMapper();
//            ResponseDto<?> responseDto = new ResponseDto<>(-1,msg,null);
//            String responseBody = om.writeValueAsString(responseDto);
//            response.setContentType("application/json; charset=utf-8");
//            response.setStatus(HttpServletResponse.SC_FORBIDDEN); //403
//            response.getWriter().println(responseBody); //포스트맨 or 웹 으로 localhost:8081/api/s/hello 이 경로를 찍어보면 error가 찍힌다.
//        }catch (Exception e){
//            log.error("서버 파싱 에러");
//        }
//
//    }
}

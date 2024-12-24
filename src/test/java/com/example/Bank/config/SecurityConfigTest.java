package com.example.Bank.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc //Mock(가짜) 환경에 MockMvc 가 등록됨
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK) //MOCK == 가짜 환경에서 테스트 한다는 뜼이다
public class SecurityConfigTest {

    //가짜 환경에 등록된 MockMvc 를 DI 함
    @Autowired
    private MockMvc mvc;

    @Test
     public void authentication_test() throws Exception {
         // given

         // when
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/api/s/hello")); // HTTP GET 요청을 생성하는 빌더 메서드
        String responsebody = resultActions.andReturn().getResponse().getContentAsString(); //요청에 대한 응답 본문 내용을 문자열로 저장
        int httpStatusCode = resultActions.andReturn().getResponse().getStatus(); //상태 코드를 반환
        System.out.println("테스트: "+ responsebody); // 바디에는 아무값도 없다. 하지만 secuirtyconfig 에서 http.exceptionHandling에서 설정을 해주면 error가 찍힌다.
        System.out.println("테스트: "+ httpStatusCode); //상태코드 에서는 403이 뜬다.

         // then

    }

    @Test
    public void authorization_test() throws Exception {
        // given

        // when

        // then

    }
}

package com.example.Bank.web;

import com.example.Bank.config.dummy.DummyObject;
import com.example.Bank.domain.user.UserRepository;
import com.example.Bank.dto.user.UserReqDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc //서블릿 컨테이너 없이, Spring MVC 컨트롤러를 직접 테스트
@SpringBootTest(webEnvironment = WebEnvironment.MOCK) //Mock 환경에서 컨트롤러를 테스트
class UserControllerTest extends DummyObject {

    @Autowired
    private MockMvc mvc; //실제 서버를 실행하지 않고도 컨트롤러를 가짜(Mock) 환경에서 호출할 수 있도록 도와 준다

    @Autowired
    private ObjectMapper om; //Jackson 라이브러리에서 제공하는 객체 - JSON 변환기

    @Autowired
    private UserRepository userRepository;

    @BeforeEach // 모든 메서드가 실행되기 직전에 실행 더미 데이터 생성
    public void setUp() {
        dataSetting();
    }

    @Test
    public void join_success_test() throws Exception {
        // given = 초기 데이터(입력값)를 준비하는 단계
        UserReqDto.JoinReqDto joinReqDto = new UserReqDto.JoinReqDto();
        joinReqDto.setUsername("love");
        joinReqDto.setPassword("1234");
        joinReqDto.setEmail("love@nate.com");
        joinReqDto.setFullname("러브");

        String requestBody = om.writeValueAsString(joinReqDto); // 객체값을 json으로 변경
//        System.out.println("테스트 :" +requestBody);

        // when = 테스트할 동작(액션)을 실행하는 단계
        // mvc.perform = 실제로 서버를 실행하지 않고, Spring MVC 내부에서 컨트롤러를 호출
        // contentType(MediaType.APPLICATION_JSON) = 이 요청은 JSON 형식입니다" 라고 알려주는 역할
        ResultActions resultActions = mvc.perform(post("/api/join").content(requestBody).contentType(MediaType.APPLICATION_JSON));
//        String responseBody =  resultActions.andReturn().getResponse().getContentAsString();
//        System.out.println("테스트 :" +responseBody);

        // then = 테스트 결과가 예상대로 나왔는지 검증하는 단계
        resultActions.andExpect(status().isCreated());
    }

    @Test
    public void join_fail_test() throws Exception {
        // given
        UserReqDto.JoinReqDto joinReqDto = new UserReqDto.JoinReqDto();
        joinReqDto.setUsername("ssar");
        joinReqDto.setPassword("1234");
        joinReqDto.setEmail("ssar@nate.com");
        joinReqDto.setFullname("쌀");

        String requestBody = om.writeValueAsString(joinReqDto); // 객체값을 json으로 변경
//        System.out.println("테스트 :" +requestBody);

        // when
        ResultActions resultActions = mvc.perform(post("/api/join").content(requestBody).contentType(MediaType.APPLICATION_JSON));
//        String responseBody =  resultActions.andReturn().getResponse().getContentAsString();
//        System.out.println("테스트 :" +responseBody);

        // then
        resultActions.andExpect(status().isBadRequest());
    }


    private void dataSetting(){
        userRepository.save(newUser("ssar","쌀"));
    }

}
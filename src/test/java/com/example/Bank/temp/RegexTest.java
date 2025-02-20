package com.example.Bank.temp;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

// java.util.regex.Pattern
public class RegexTest {

    //정규표현식
    // ^ = ex) 한글로 시작하는것
    // $ = ex) 한글로 끝나는것
    @Test
    public void 한글만된다_test() throws Exception {
        String value = "한글";
        Boolean result = Pattern.matches("^[가-힣]+$",value); //가 -힣 = 전체가  한글을 뜻한다 그리고 한글 한글자 만 된다
        System.out.println("테스트 :"+result);
    }

    @Test
    public void 한글만안된다_test() throws Exception {
        // given

        // when

        // then

    }

    @Test
    public void 영어만된다_test() throws Exception {
        // given

        // when

        // then

    }

    @Test
    public void 영어는안된다_test() throws Exception {
        // given

        // when

        // then

    }

    @Test
    public void 영어와숫자만된다_test() throws Exception {
        // given

        // when

        // then

    }

    @Test
    public void 영어만되고_길이는최소2최대4이다_test() throws Exception {
        // given

        // when

        // then

    }
}

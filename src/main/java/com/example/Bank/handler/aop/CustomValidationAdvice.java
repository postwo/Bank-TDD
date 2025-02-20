package com.example.Bank.handler.aop;

import com.example.Bank.handler.ex.CustomValidationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Component //설정파일도 아니고 컨트롤러나 서비스도 아니여서 component를 사용
@Aspect //스프링 AOP(Aspect-Oriented Programming)에서 사용되는 어노테이션입니다. AOP는 횡단 관심사(공통적인 로직)를 모듈화하여 주업무 로직과 분리할 수 있도록 도운다
public class CustomValidationAdvice {

    /* get,delete 는 body 데이터가 없다
   post,put 은 body 데이터가 있다  그러므로 body 데이터 2개만 유효성검사가 필요하다
    * */

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postMapping() {}

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void putMapping() {}

    // 중요 @Pointcut으로 정의한 메서드 이름을 @Around 어드바이스에 사용하여 포인트컷을 지정

    // Around는 joinpoint(joinpoint== 메서드)의 전후 제어가 가능하다
    //*postMapping,putMapping 이라는 어노테이션이 붙은 모든 컨트롤러가 실행이 될때 동작을 한다 근데 BindingResult라는 매개변수가 있으면  if (arg instanceof BindingResult) 이게 동작을 한다 *
    //*error가 있을때 throw new CustomValidationException("유효성 검사 실패",errorMap);를 날린다
    @Around("postMapping() || putMapping()") //포스트 혹은 풋매핑 을 어드바이스 하겠다는거다
    public Object validationAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Object[] args = proceedingJoinPoint.getArgs(); //joinpoint(joinpoint== 메서드) 매개변수를 뜻한다

        for (Object arg : args){
            if (arg instanceof BindingResult){ //arg에 BindingResult가 있으면 동작 한다는 의미이다

                BindingResult bindingResult = (BindingResult) arg; //다운 캐스팅

                if (bindingResult.hasErrors()){
                    Map<String,String> errorMap = new HashMap<>();

                    for (FieldError errorr : bindingResult.getFieldErrors()){
                        errorMap.put(errorr.getField(),errorr.getDefaultMessage());
                    }
                    throw new CustomValidationException("유효성 검사 실패",errorMap);
                }
            }
        }
        return proceedingJoinPoint.proceed(); // 정상적으로 해당 메서드를 실행해라
    }
}


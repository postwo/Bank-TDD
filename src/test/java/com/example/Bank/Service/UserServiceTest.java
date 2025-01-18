package com.example.Bank.Service;

import com.example.Bank.Service.UserService.JoinReqDto;
import com.example.Bank.Service.UserService.JoinRespDto;
import com.example.Bank.domain.user.User;
import com.example.Bank.domain.user.UserEnum;
import com.example.Bank.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

// spring 관련 bean들이 하나도 없는 환경
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks //가짜 환경을 만드는거다
    private UserService userService;

    @Mock //가짜로 메모리에 띄운다 이거를 InjectMocks == userService 에 주입을 해준다
    private UserRepository userRepository;

    @Spy //진짜를 띄운다 == spring ioc에 있는걸 가지고 와서 userService에 넣어주는거다
    private BCryptPasswordEncoder passwordEncoder;

     @Test
      public void 회원가입_test() throws Exception {
         // given 은 매개변수를 뜻한다
         JoinReqDto joinReqDto = new JoinReqDto();
         joinReqDto.setUsername("ssar");
         joinReqDto.setPassword("1234");
         joinReqDto.setEmail("ssar@nate.com");
         joinReqDto.setFullname("쌀");

         // stub1 stub은 가설 같은거다. any는 뭐라도 들어가게 하는거다   Optional.empty 빈optional 객체를 반환한다는 의미다
         when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
         //when(userRepository.findByUsername(any())).thenReturn(Optional.of(new User())); //이거는 user라는 값이 있는것이다. 이렇게 하면 커스텀 exception이 터진다

         // stub1 여기는 ssar이 리턴된다.
         User ssar = User.builder()
                         .id(1L)
                         .username("ssar")
                         .password("1234")
                         .email("ssar@nate.com")
                         .fullname("쌀")
                         .role(UserEnum.CUSTOMER)
                         .createdAt(LocalDateTime.now())
                         .updatedAt(LocalDateTime.now())
                         .build();
         when(userRepository.save(any())).thenReturn(ssar);

         // when 회원가입 실행
         JoinRespDto joinRespDto = userService.회원가입(joinReqDto);
         System.out.println("테스트 :"+joinRespDto);

         // then 검증
         assertThat(joinRespDto.getId()).isEqualTo(1L);
         assertThat(joinRespDto.getUsername()).isEqualTo("ssar");

     }

}
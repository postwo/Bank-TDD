package com.example.Bank.dto.user;

import com.example.Bank.domain.user.User;
import com.example.Bank.domain.user.UserEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserReqDto {

    @Getter
    @Setter
    public static class LoginReqDto {
        private String username;
        private String password;
    }

    @Setter
    @Getter
    public static class JoinReqDto{

        @NotEmpty //null,공백 일 수 없다
        @Pattern(regexp = "^[a-zA-Z0-9]{2,20}$", message ="영문/숫자 2~20자 이내로 작성해주세요")
        private String username;

        @NotEmpty
        @Size(min = 4, max =20) // 사이즈 스트링전용
        private String password;


        @NotEmpty
        @Pattern(regexp = "^[a-zA-Z0-9]{2,10}@[a-zA-Z0-9]{2,6}\\.[a-zA-Z]{2,3}$", message ="이메일 형식으로 작성해주세요")
        private String email;


        @NotEmpty
        @Pattern(regexp = "^[a-zA-Z가-힣]{1,20}$", message ="한글/영문 1~20 자 이내로 작성해주세요")
        private String fullname;

        public User toEntity(BCryptPasswordEncoder passwordEncoder){
            return User.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .email(email)
                    .fullname(fullname)
                    .role(UserEnum.CUSTOMER)
                    .build();
        }
    }
}

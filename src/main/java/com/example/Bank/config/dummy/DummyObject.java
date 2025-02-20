package com.example.Bank.config.dummy;

import com.example.Bank.domain.user.User;
import com.example.Bank.domain.user.UserEnum;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

public class DummyObject {

    protected User newUser(String username,String fullname) { // 여기 id,날짜를 뺀 이유는 save하면 아이디,날짜가 자동으로 만들어지기 때문이다
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return   User.builder()
                .username(username)
                .password(passwordEncoder.encode("1234"))
                .email(username+"@nate.com")
                .fullname(fullname)
                .role(UserEnum.CUSTOMER)
                .build();
    }

    protected User newMockUser(Long id,String username,String fullname) { //가짜 유저
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return   User.builder()
                .id(id)
                .username(username)
                .password(passwordEncoder.encode("1234"))
                .email(username+"@nate.com")
                .fullname(fullname)
                .role(UserEnum.CUSTOMER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}

package com.example.Bank.config.auth;

import com.example.Bank.domain.user.User;
import com.example.Bank.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

    private final UserRepository userRepository;

    // 이거는 로그인 될때 세션을 만들어준다
    // 시큐리티로 로그인이 될때 시큐리티가 loadUserByUsername 실행해서 username을 체크
    // 체크해서 없으면 오류  ,  있으면 정상적으로 시큐리티 컨텍스트 내부 세션에 로그인된 세션이 만들어진다
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userPs = userRepository.findByUsername(username).orElseThrow(
                ()-> new InternalAuthenticationServiceException("인증 실패")
        );
        return new LoginUser(userPs); //new LoginUser(userPs) 이 객체가 세션에 만들어진다
    }
}

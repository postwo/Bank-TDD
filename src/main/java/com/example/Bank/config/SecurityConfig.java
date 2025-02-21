package com.example.Bank.config;

import com.example.Bank.domain.user.UserEnum;
import com.example.Bank.util.CustomResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {
    // Logger를 사용하는 방법: @Slf4j 어노테이션을 사용할 수도 있습니다.
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Bean // IoC 컨테이너에 BCryptPasswordEncoder 객체가 등록됨
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        log.debug("디버그 : BCryptPasswordEncoder 빈 등록됨");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.debug("디버그: filterChain 빈 등록됨");

        // frameOptions 설정을 새롭게 적용
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));

        // CSRF 설정 비활성화
        http.csrf(AbstractHttpConfigurer::disable);

        // CORS 설정
        http.cors(cors -> cors.configurationSource(configurationSource()));

        // jSessionId를 서버에서 관리하지 않하겠다는 뜻
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // React 및 앱에서 요청할 예정이므로 formLogin과 httpBasic 비활성화
        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);

        // Exception 가로채기
       http.exceptionHandling(exceptionHandling ->
                exceptionHandling.authenticationEntryPoint((request, response, authException) -> {
                    CustomResponseUtil.unAuthentication(response,"로그인을 진행해 주세요");
                })

        );

        // https://docs.spring.io/spring-security/reference/servlet/authorization/authorize-http-requests.html
        // 권한 및 인증 설정
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(new AntPathRequestMatcher("/api/s/**")).authenticated()
                .requestMatchers(new AntPathRequestMatcher("/api/admin/**")).hasRole(UserEnum.ADMIN.name())
                .anyRequest().permitAll()
        );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource configurationSource() {
        log.debug("디버그 : configurationSource cors 설정이 SecurityFilterChain에 등록됨");

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*"); // 모든 HTTP 메서드 허용 (GET, POST, PUT, DELETE)
        configuration.addAllowedOriginPattern("*"); // 모든 IP 주소 허용 (프론트엔드 IP만 허용 react)
        configuration.setAllowCredentials(true); // 클라이언트에서 쿠키 요청 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 요청에 대해 CORS 설정 적용

        return source;
    }
}




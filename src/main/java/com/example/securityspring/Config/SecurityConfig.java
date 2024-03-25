package com.example.securityspring.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
                // 순서를 유의해서 작성.
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/","/login","/join","/joinProc").permitAll() //모든 사용자 (로그인 x) 접근 허용
                        .requestMatchers("/admin").hasRole("ADMIN") //특정 룰이 있으면 접근 가능.
                        .requestMatchers("/my/**").hasAnyRole("ADMIN","USER") // 다수의 특정 룰이 있으면 접근 가능.
                        .anyRequest().authenticated() // 로그인 한 모든 유저 접근 가능.
                );

        // 커스텀 로그인. (http basic방식)
        http
                .httpBasic(Customizer.withDefaults());
        // formlogin 방식.
//        http
//                .formLogin((auth) -> auth.loginPage("/login")
//                        .loginProcessingUrl("/loginProc")
//                        .permitAll());
        http
                .logout((auth)->auth
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                );
        // 개발환경에서는 disable. -> 자동으로 enable이 됨.
        // 배포하려면, csrf 토큰 등을 설정해야한다.
//        http
//                .csrf((auth) -> auth.disable());

        http
                .sessionManagement((auth) -> auth
                        .maximumSessions(1) // 하나의 아이디의 다중 로그인 허용 개수. (여러 브라우저 접속)
                        .maxSessionsPreventsLogin(true)); // 다중 로그인이 거부시의 전략, true는 로그인을 막고, false는 기존의 로그인을 제거하는 것임.

        //세션 고정 보호
        http
                .sessionManagement((auth) -> auth
                        .sessionFixation().changeSessionId()); // 기존 세션의 쿠키 id를 변경.

        return http.build();
    }
}

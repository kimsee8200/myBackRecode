package com.example.securityspring.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                        .requestMatchers("/","/login","/joinProc","/join","/joinProc").permitAll() //모든 사용자 (로그인 x) 접근 허용
                        .requestMatchers("/admin").hasRole("ADMIN") //특정 룰이 있으면 접근 가능.
                        .requestMatchers("/my/**").hasAnyRole("ADMIN","USER") // 다수의 특정 룰이 있으면 접근 가능.
                        .anyRequest().authenticated() // 로그인 한 모든 유저 접근 가능.
                );

        http
                .formLogin((auth) -> auth.loginPage("/login")
                        .loginProcessingUrl("/loginProc")
                        .permitAll()
                );

        http
                .csrf((auth) -> auth.disable());



        return http.build();
    }
}

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
                // ������ �����ؼ� �ۼ�.
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/","/login","/joinProc","/join","/joinProc").permitAll() //��� ����� (�α��� x) ���� ���
                        .requestMatchers("/admin").hasRole("ADMIN") //Ư�� ���� ������ ���� ����.
                        .requestMatchers("/my/**").hasAnyRole("ADMIN","USER") // �ټ��� Ư�� ���� ������ ���� ����.
                        .anyRequest().authenticated() // �α��� �� ��� ���� ���� ����.
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

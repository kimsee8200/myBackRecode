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
                // ������ �����ؼ� �ۼ�.
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/","/login","/join","/joinProc").permitAll() //��� ����� (�α��� x) ���� ���
                        .requestMatchers("/admin").hasRole("ADMIN") //Ư�� ���� ������ ���� ����.
                        .requestMatchers("/my/**").hasAnyRole("ADMIN","USER") // �ټ��� Ư�� ���� ������ ���� ����.
                        .anyRequest().authenticated() // �α��� �� ��� ���� ���� ����.
                );

        // Ŀ���� �α���. (http basic���)
        http
                .httpBasic(Customizer.withDefaults());
        // formlogin ���.
//        http
//                .formLogin((auth) -> auth.loginPage("/login")
//                        .loginProcessingUrl("/loginProc")
//                        .permitAll());
        http
                .logout((auth)->auth
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                );
        // ����ȯ�濡���� disable. -> �ڵ����� enable�� ��.
        // �����Ϸ���, csrf ��ū ���� �����ؾ��Ѵ�.
//        http
//                .csrf((auth) -> auth.disable());

        http
                .sessionManagement((auth) -> auth
                        .maximumSessions(1) // �ϳ��� ���̵��� ���� �α��� ��� ����. (���� ������ ����)
                        .maxSessionsPreventsLogin(true)); // ���� �α����� �źν��� ����, true�� �α����� ����, false�� ������ �α����� �����ϴ� ����.

        //���� ���� ��ȣ
        http
                .sessionManagement((auth) -> auth
                        .sessionFixation().changeSessionId()); // ���� ������ ��Ű id�� ����.

        return http.build();
    }
}

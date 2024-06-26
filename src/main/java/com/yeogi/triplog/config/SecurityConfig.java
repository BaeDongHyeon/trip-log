package com.yeogi.triplog.config;

import com.yeogi.triplog.config.handler.CustomAuthFailureHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/h2-console/**", "/signup", "/login", "/css/**", "/js/**", "/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")    // 로그인 페이지 설정
                        .loginProcessingUrl("/login/process")
                        .failureHandler(failureHandler())
                        .usernameParameter("email")
                        .defaultSuccessUrl("/") // 로그인 성공시 redirect URL
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")   // 로그아웃 URL
                        .logoutSuccessUrl("/")  // 로그아웃 성공시 redirect URL
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable()) // csrf 보호 비활성화
                .headers(headers -> headers
                        .frameOptions(frameOptionsConfig -> frameOptionsConfig.sameOrigin()));   // H2 콘솔 사용을 위한 설정
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return new CustomAuthFailureHandler();
    }
}

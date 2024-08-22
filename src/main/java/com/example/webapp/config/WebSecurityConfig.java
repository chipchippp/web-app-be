package com.example.webapp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
//@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http
                 .csrf(AbstractHttpConfigurer::disable)
                 .authorizeRequests(authorizeRequests ->
                        authorizeRequests

                                .requestMatchers("**").permitAll()
                 )
                 .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .permitAll()
                 )
                 .logout(logout ->
                        logout
                                .logoutUrl("/logout")
                                .permitAll()
                 );
         return http.build();
    }
}

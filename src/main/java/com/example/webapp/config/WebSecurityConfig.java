package com.example.webapp.config;

import com.example.webapp.filters.JwtTokenFilter;
import com.example.webapp.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
//@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    @Value("${api.prefix}")
    private String apiPrefix;
    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http
                 .csrf(AbstractHttpConfigurer::disable)
                 .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                 .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(String.format("%s/users/login", apiPrefix),
                                        String.format("%s/users/register", apiPrefix))
                                .permitAll()
                                .requestMatchers(GET, String.format("%s/orders/user/**", apiPrefix)).hasAnyRole(Role.USER, Role.ADMIN)
                                .requestMatchers(POST, String.format("%s/orders/**", apiPrefix)).hasAnyRole(Role.USER)
                                .requestMatchers(PUT, String.format("%s/orders/**", apiPrefix)).hasRole(Role.ADMIN)
                                .requestMatchers(DELETE, String.format("%s/orders/**", apiPrefix)).hasRole(Role.ADMIN)

                                .anyRequest().authenticated()

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

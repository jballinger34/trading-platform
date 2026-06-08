package com.mthree.TradingPlatform.config;

import com.mthree.TradingPlatform.handler.GithubLoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;

@Configuration
public class SecurityConfig {

    private final GithubLoginSuccessHandler successHandler;

    public SecurityConfig(GithubLoginSuccessHandler successHandler) {
        this.successHandler = successHandler;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/").permitAll()
                        .anyRequest().authenticated())
                .oauth2Login(oauth -> oauth
                        .successHandler(successHandler));
        return httpSecurity.build();
    }

}

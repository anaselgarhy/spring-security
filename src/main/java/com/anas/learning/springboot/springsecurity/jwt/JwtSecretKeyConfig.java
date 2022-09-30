package com.anas.learning.springboot.springsecurity.jwt;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 30/09/2022
 */
@Configuration
public class JwtSecretKeyConfig {
    private final JwtConfig jwtConfig;

    @Autowired
    public JwtSecretKeyConfig(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Bean
    public SecretKey secretKey() {
        return Keys.hmacShaKeyFor(
                    jwtConfig.getSecretKey().getBytes()
        );
    }
}

package com.ssafy.tlens.config;

import com.ssafy.tlens.common.RedisDao;
import com.ssafy.tlens.config.jwt.JwtAuthenticationFilter;
import com.ssafy.tlens.config.jwt.JwtAuthorizationFilter;
import com.ssafy.tlens.config.jwt.JwtProvider;
import com.ssafy.tlens.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class JwtFilterConfig extends AbstractHttpConfigurer<JwtFilterConfig, HttpSecurity> {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final RedisTemplate<String, String> redisTemplate;
    private final RedisDao redisDao;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        http
                .addFilter(new JwtAuthenticationFilter(authenticationManager, jwtProvider,redisTemplate, redisDao))
                .addFilter(new JwtAuthorizationFilter(authenticationManager, userRepository,jwtProvider, redisDao));
    }

}

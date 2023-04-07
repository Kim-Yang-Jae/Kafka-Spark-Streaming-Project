package com.ssafy.tlens.config;

import java.util.Arrays;
import java.util.List;

import com.ssafy.tlens.config.jwt.JwtProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
//        config.setAllowedOrigins(List.of("http://localhost:3000/"));
        config.addAllowedOriginPattern("*"); // 새로 추가한 부분이고 밑의 addAllowedOrigin은 주석처리함
//        config.addAllowedOrigin("*"); // e.g. http://domain1.com
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setExposedHeaders(Arrays.asList("Content-Type", JwtProperties.HEADER_STRING, "ATK", "RTK"));

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
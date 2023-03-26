package com.ssafy.tlens.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Swagger springdoc-ui 구성 파일
 */
@Configuration
@EnableWebMvc
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("T:LENS API Document")
                .version("v1.0.0")
                .description("SSAFY 특화 프로젝트 T:LENS의 API 명세서입니다.");
        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
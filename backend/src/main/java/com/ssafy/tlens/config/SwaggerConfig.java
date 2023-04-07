package com.ssafy.tlens.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
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
                .addServersItem(new Server().url("https://j8c206.p.ssafy.io/api/v1"))
                .addServersItem(new Server().url("http://localhost:8080/api/v1"))
                .components(new Components())
                .info(info);
    }
}
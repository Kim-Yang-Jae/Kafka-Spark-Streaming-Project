package com.ssafy.tlens.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity  // 1-1. 시큐리티 활성화 -> 기본 스프링 필터체인에 등록
@EnableGlobalMethodSecurity(prePostEnabled = true)
// WebSecurityConfigurerAdapter depreated 2.7.0
// SecurityFilterChain 권장
// https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
public class SecurityConfig {

    @Autowired
    private JwtFilterConfig jwtFilterConfig;

    @Autowired
    private CorsConfig corsConfig;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .addFilter(corsConfig.corsFilter())                     // cors 설정
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .apply(jwtFilterConfig)
                .and()
                .authorizeRequests()// httpservletrequest로 접근하는 것을 제한하겠다.
                // "/api/user/test" permitAll 을 "/api/user/**" .hasRole("USER") 뒤에 하면 안먹힘!
                .antMatchers( "/swagger-ui.html", "/swagger/**", "/swagger-resources" ,"/swagger-resources/**","/webjars/**", "/swagger-ui/**").permitAll()
                .antMatchers("/users", "/users/login").permitAll()
                .antMatchers("/users/join","/user/nicknameCk").permitAll()
//                .antMatchers("/api/user/**").hasRole(UserRoleType.USER.getValue())
                .anyRequest().authenticated();
//                .and()
//                .apply(jwtFilterConfig);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // filter 자체를 안탐
        return (web) -> web
                .ignoring()
                .antMatchers("/api/file/**","/api/product/category");
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

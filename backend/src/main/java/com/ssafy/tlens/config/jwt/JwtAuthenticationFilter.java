package com.ssafy.tlens.config.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.tlens.common.RedisDao;
import com.ssafy.tlens.common.ResponseDto;
import com.ssafy.tlens.config.auth.PrincipalDetails;
import com.ssafy.tlens.api.request.LoginRequestDto;
import com.ssafy.tlens.api.response.LoginRespDto;
import com.ssafy.tlens.enums.ResponseEnum;
import com.ssafy.tlens.handler.exception.CustomAuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

// 로그인 인증과정
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RedisTemplate<String, String> redisTemplate;
    private final RedisDao redisDao;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtProvider jwtProvider, RedisTemplate<String, String> redisTemplate, RedisDao redisDao) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.redisTemplate = redisTemplate;
        this.redisDao = redisDao;
        setFilterProcessesUrl("/users/login");
//        setFilterProcessesUrl("/api/v1/login"); 일 때 포스트맨
//        http://localhost:8080/api/v1/api/v1/login 작동함
    }

    // Authentication 객체 만들어서 리턴 => 의존 : AuthenticationManager
    // 인증 요청시에 실행되는 함수 => /login
    // refresh, login
    // Post : /api/v1/login
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        log.debug("디버그 : attemptAuthentication 호출됨");
        System.out.println("JwtAuthenticationFilter : 진입");

        // request에 있는 username과 password를 파싱해서 자바 Object로 받기
        ObjectMapper om = new ObjectMapper();
        LoginRequestDto loginRequestDto = null;
        try {
            loginRequestDto = om.readValue(request.getInputStream(), LoginRequestDto.class);
        } catch (Exception e) {
            throw new CustomAuthenticationException(ResponseEnum.AUTH_BAD_REQUEST);
        }

        System.out.println("JwtAuthenticationFilter : "+loginRequestDto);

//        String userEmail = null;
//
//        userEmail = loginRequestDto.getEmail();

//        if(loginRequestDto.getLoginType().equals(LoginType.LOGIN)){
//            // 로그인
////            KakaoUserInfoDto kakaoUserInfoDto = kakaoProvider.login(loginRequestDto.getToken());
////            if(kakaoUserInfoDto == null){
////                throw new CustomAuthenticationException(ResponseEnum.AUTH_INVALID_TOKEN);
////            }
//            userEmail = loginRequestDto.getEmail();
//        }else{
//            // refresh
//            String refreshToken = redisTemplate.opsForValue().getAndDelete(loginRequestDto.getToken());
//            if(refreshToken == null){
//                throw new CustomAuthenticationException(ResponseEnum.AUTH_REFRESH_DOES_NOT_EXIST);
//            }
//            userEmail = jwtProvider.getUserEmail(refreshToken);
//        }

        // 유저네임패스워드 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getEmail(),
                        loginRequestDto.getPassword());

        System.out.println("JwtAuthenticationFilter : 토큰생성완료");

        // authenticate() 함수가 호출 되면 인증 프로바이더가 유저 디테일 서비스의
        // loadUserByUsername(토큰의 첫번째 파라메터) 를 호출하고
        // UserDetails를 리턴받아서 토큰의 두번째 파라메터(credential)과
        // UserDetails(DB값)의 getPassword()함수로 비교해서 동일하면
        // Authentication 객체를 만들어서 필터체인으로 리턴해준다.

        // Tip: 인증 프로바이더의 디폴트 서비스는 UserDetailsService 타입
        // Tip: 인증 프로바이더의 디폴트 암호화 방식은 BCryptPasswordEncoder
        // 결론은 인증 프로바이더에게 알려줄 필요가 없음.
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(authenticationToken);
        }catch (Exception e){
            e.printStackTrace();
            throw new CustomAuthenticationException(ResponseEnum.AUTH_NOT_JOINED);
        }

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("Authentication getEmail : "+principalDetails.getUser().getEmail());
        return authentication;
    }


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        CustomAuthenticationException exception = ((CustomAuthenticationException) failed);
        ObjectMapper om = new ObjectMapper();
        String responseBody = om.writer().writeValueAsString(new ResponseDto<>(exception.getResponseEnum()));
        response.getWriter().println(responseBody);
        response.setStatus(exception.getResponseEnum().getCode());
        response.setContentType("application/json");
    }

    // JWT Token 생성해서 response에 담아주기
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        log.debug("디버그 : successfulAuthentication 호출됨");
        System.out.println("successfulAuthentication");
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
        LoginRespDto loginRespDto = new LoginRespDto(principalDetails.getUser());

        String accessToken = jwtProvider.createAccessToken(principalDetails.getUser().getUserId(),principalDetails.getUser().getEmail());
        String refreshToken = jwtProvider.createRefreshToken(principalDetails.getUser().getUserId(),principalDetails.getUser().getEmail());
//        redisTemplate.opsForValue().set(accessToken, refreshToken);

        long now = (new Date()).getTime();
        Date refreshTokenExpiresIn = new Date(now + JwtProperties.EXPIRATION_TIME_REFRESH);
//        System.out.println("DATE refreshTokenExpiresIn : " + refreshTokenExpiresIn);
//        System.out.println("refreshTokenExpiresIn.getTime() : " + (refreshTokenExpiresIn.getTime()));
//        System.out.println("Duration.ofMillis(refreshTokenExpiresIn.getTime()) : " + Duration.ofMillis(refreshTokenExpiresIn.getTime()));
        redisDao.setValues(principalDetails.getUser().getEmail(), refreshToken, refreshTokenExpiresIn.getTime());

        response.addHeader(JwtProperties.ACCESS_TOKEN, JwtProperties.TOKEN_PREFIX+accessToken);
        response.addHeader(JwtProperties.REFRESH_TOKEN, JwtProperties.TOKEN_PREFIX+refreshToken);

        try {
            ObjectMapper om = new ObjectMapper();
            ResponseDto<?> responseDto = new ResponseDto<>(1, "로그인성공", loginRespDto);
            String responseBody = om.writeValueAsString(responseDto);
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(200);
            response.getWriter().println(responseBody); // 예쁘게 메시지를 포장하는 공통적인 응답 DTO를 만들어보자!!
        } catch (Exception e) {
            log.error("서버 파싱 에러");
        }



    }

}
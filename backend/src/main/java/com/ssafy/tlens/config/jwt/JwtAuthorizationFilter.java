package com.ssafy.tlens.config.jwt;

import com.ssafy.tlens.common.RedisDao;
import com.ssafy.tlens.config.auth.PrincipalDetails;
import com.ssafy.tlens.entity.rdbms.User;
import com.ssafy.tlens.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.ObjectUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

// 인가
public class JwtAuthorizationFilter extends BasicAuthenticationFilter{

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final RedisDao redisDao;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository, JwtProvider jwtProvider, RedisDao redisDao) {
        super(authenticationManager);
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.redisDao = redisDao;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        System.out.println("doFilterInternal");
        String header = request.getHeader(JwtProperties.HEADER_STRING);
        if(header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            System.out.println("header 인증x -> doFilter");
            chain.doFilter(request, response);
            return;
        }
        System.out.println("header : "+header);

        String token = request.getHeader(JwtProperties.HEADER_STRING)
                .replace(JwtProperties.TOKEN_PREFIX, "");

        // (추가) Redis 에 해당 accessToken logout 여부 확인
        //블랙리스트에 추가된 토큰이라면 리턴함
        String isLogout = (String)redisDao.getValues(token);
        if (!ObjectUtils.isEmpty(isLogout)){
            System.out.println("블랙리스트에 걸림");
            chain.doFilter(request, response);
            return;
        }

        // 토큰 검증 (이게 인증이기 때문에 AuthenticationManager도 필요 없음)
        // 내가 SecurityContext에 직접 접근해서 세션을 만들때 자동으로 UserDetailsService에 있는 loadByUsername이 호출됨.

        String userEmail = jwtProvider.getUserEmail(token);
        System.out.println("userEmail : "+ userEmail);
        String type = jwtProvider.getType(token);
        String requestURI = request.getRequestURI();

        if (type.equals("RTK") && !requestURI.equals("/api/v1/users/reissue")) {
            System.out.println("RTK 타입일 땐 /reissue 요청만 받을 수 있다.");
            chain.doFilter(request, response);
            return;
        }


        if(userEmail != null) {
            Optional<User> user = userRepository.findByEmail(userEmail);

            // 인증은 토큰 검증시 끝. 인증을 하기 위해서가 아닌 스프링 시큐리티가 수행해주는 권한 처리를 위해
            // 아래와 같이 토큰을 만들어서 Authentication 객체를 강제로 만들고 그걸 세션에 저장!
            PrincipalDetails principalDetails = new PrincipalDetails(user.get());
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(
                            principalDetails, //나중에 컨트롤러에서 DI해서 쓸 때 사용하기 편함.
                            null, // 패스워드는 모르니까 null 처리, 어차피 지금 인증하는게 아니니까!!
                            principalDetails.getAuthorities());

            // 강제로 시큐리티의 세션에 접근하여 값 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
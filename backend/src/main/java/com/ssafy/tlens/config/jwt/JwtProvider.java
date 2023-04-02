package com.ssafy.tlens.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ssafy.tlens.common.RedisDao;
import com.ssafy.tlens.entity.rdbms.User;
import com.ssafy.tlens.enums.ResponseEnum;
import com.ssafy.tlens.handler.exception.CustomAuthenticationException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final RedisDao redisDao;

    public String getUserEmail(String jwt) {
        return JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(jwt)
                .getClaim("email").asString();
    }

    public String getType(String jwt) {
        try {
            return JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(jwt)
                    .getClaim("type").asString();
        }catch (Exception e){
            throw new CustomAuthenticationException(ResponseEnum.TOKEN_TYPE_NOT_FOUND);
        }
    }

    public String reissueAtk(User user, String reqRTK){
        //Redis 에서 User email 을 기반으로 저장된 Refresh Token 값을 가져옵니다.
        String rtkInRedis = redisDao.getValues(user.getEmail());
        System.out.println("rtkInRedis: " +rtkInRedis);
        //(추가) 로그아웃되어 Redis 에 RefreshToken 이 존재하지 않는 경우 처리
        if (Objects.isNull(rtkInRedis)) throw new CustomAuthenticationException(ResponseEnum.REDIS_USER_NOT_FOUND);
        //요청으로 받은 reqRTK의 검증은 이미 끝났고 reqRTK 탈취 여부를 확인하기 위해
        //redis 저장해둔 해당 email을 key로 하는 rtkInRedis와 비교함
        if(!rtkInRedis.equals(reqRTK)) throw new CustomAuthenticationException(ResponseEnum.RTK_NOT_MATCHED);
//        Subject atkSubject = Subject.atk(
//                accountResponse.getAccountId(),
//                accountResponse.getEmail(),
//                accountResponse.getNickname());
        String atk = createAccessToken(user.getUserId(), user.getEmail());
        return atk;
    }


    public String createAccessToken(Long userId, String email){
        return createToken(userId,email,JwtProperties.EXPIRATION_TIME, "ATK");
    }

    public String createRefreshToken(Long userId, String email){
        return createToken(userId,email,JwtProperties.EXPIRATION_TIME_REFRESH, "RTK");
    }

    private String createToken(Long userId, String email, Long expirationTime, String type){
        return JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis()+expirationTime))
                .withClaim("id", userId)
                .withClaim("email", email)
                .withClaim("type", type)
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
    }
    public Long getExpiration(String accessToken) {
        DecodedJWT jwt = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
                .build()
                .verify(accessToken);
        // accessToken 남은 유효시간
        Date expiresAt = jwt.getExpiresAt();
        // 현재 시간
        Long now = new Date().getTime();
        //현재 시간을 빼서 만료까지 남은 시간을 구한다.
        return (expiresAt.getTime() - now);
    }
}

package com.ssafy.tlens.api.controller;

import com.ssafy.tlens.api.request.ScrapRequestDTO;
import com.ssafy.tlens.api.response.KeywordResponseDTO;
import com.ssafy.tlens.api.response.NewsInfoDTO;
import com.ssafy.tlens.api.service.KeywordSerivceImpl;
import com.ssafy.tlens.common.ResponseDto;
import com.ssafy.tlens.common.model.response.HttpResponseEntity;
import com.ssafy.tlens.config.auth.PrincipalDetails;
import com.ssafy.tlens.config.jwt.JwtProperties;
import com.ssafy.tlens.config.jwt.JwtProvider;
import com.ssafy.tlens.api.request.SignUpRequestDto;
import com.ssafy.tlens.enums.ResponseEnum;
import com.ssafy.tlens.api.service.UserServiceImpl;
import com.ssafy.tlens.handler.exception.CustomApiException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import static com.ssafy.tlens.common.model.response.HttpResponseEntity.success;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;
    private final JwtProvider jwtProvider;

    private final KeywordSerivceImpl keywordSerivce;

    @PostMapping
    @ApiOperation(value = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(code=200,message="정상적으로 회원가입이 되었습니다.", response = ResponseEntity.class)
    })
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        userService.signUp(signUpRequestDto);
        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.USER_JOIN_SUCCESS), HttpStatus.OK);
    }
    @GetMapping("/reissue")
    public ResponseEntity<?> reissue(
            @AuthenticationPrincipal PrincipalDetails principalDetails, HttpServletRequest request
    ) {
        System.out.println("reissue controller 진입: " + principalDetails.getUser().getUserId() + " " + principalDetails.getUser().getEmail());
        try {
            String reqRTK = request.getHeader(JwtProperties.HEADER_STRING)
                    .replace(JwtProperties.TOKEN_PREFIX, "");
            String atk= JwtProperties.TOKEN_PREFIX+jwtProvider.reissueAtk(principalDetails.getUser(), reqRTK);
            return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.ATK_REISSUE_SUCCESS, atk), HttpStatus.OK);
        }catch (Exception e){
            // /users/reissue는 permitAll 설정을 해놓고 refresh토큰의 유효성 검증에 대한 에러처리는 여기서 한다.
            //만료된 refresh 토큰
            throw new CustomApiException(ResponseEnum.AUTH_REFRESH_EXPIRED);
        }
    }
    // 로그아웃
    @GetMapping("/logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal PrincipalDetails principalDetails, HttpServletRequest request) {
        String reqATK = request.getHeader(JwtProperties.HEADER_STRING)
                .replace(JwtProperties.TOKEN_PREFIX, "");
        userService.logout(principalDetails.getUser().getEmail(), reqATK);

        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.USER_LOGOUT_SUCCESS), HttpStatus.OK);
    }

    @GetMapping("/{rawPwd}")
    @ApiOperation(value = "비밀번호 변경 전 확인")
    public ResponseEntity<?> confirm(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable String rawPwd) {
        String email = principalDetails.getUser().getEmail();
        return userService.confirm(email, rawPwd);
    }
    @PutMapping("/{rawPwd}")
    @ApiOperation(value = "비밀번호 변경")
    public ResponseEntity<?> update(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable String rawPwd) {
        String email = principalDetails.getUser().getEmail();
        userService.update(email, rawPwd);

        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.USER_USERNAME_CK_SUCCESS), HttpStatus.OK);
    }

    @DeleteMapping
    @ApiOperation( value = "회원탈퇴" )
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "회원탈퇴 성공", response = ResponseEntity.class)
    })
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal PrincipalDetails principalDetails, HttpServletRequest request) {
        String email = principalDetails.getUser().getEmail();
        String reqATK = request.getHeader(JwtProperties.HEADER_STRING)
                .replace(JwtProperties.TOKEN_PREFIX, "");
        userService.logout(principalDetails.getUser().getEmail(), reqATK);

        return userService.deleteUser(email);
    }


    @GetMapping("/keyword")
    public ResponseEntity<?> getKeywordByUser(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<KeywordResponseDTO> result = userService.getKeywordByUser(principalDetails.getUser().getUserId());

        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.PRODUCT_CATEGORY_SUCCESS, result), HttpStatus.OK);
    }

    @GetMapping("/keyword/status")
    public ResponseEntity<?> getKeywordStatusByUser(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestParam String name) {
        Boolean result = keywordSerivce.getKeywordStatusByUser(principalDetails.getUser().getUserId(), name);

        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.PRODUCT_CATEGORY_SUCCESS, result), HttpStatus.OK);
    }

    @PostMapping("/keyword")
    public HttpResponseEntity.ResponseResult<?> insert(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestParam String keyword) throws Exception {
        keywordSerivce.insert(principalDetails.getUser().getUserId(), keyword);
        return success();
    }

    @DeleteMapping("/keyword")
    public HttpResponseEntity.ResponseResult<?> delete(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestParam String keyword) {
        keywordSerivce.delete(principalDetails.getUser().getUserId(), keyword);
        return success();
    }
}

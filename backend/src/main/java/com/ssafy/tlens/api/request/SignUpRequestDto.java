package com.ssafy.tlens.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel
public class SignUpRequestDto {
    @ApiModelProperty(name = "이메일", required = true)
    private String email;
    @ApiModelProperty(name = "비밀번호", required = true)
    private String password;
    @ApiModelProperty(name = "닉네임", required = true)
    private String nickname;
    @ApiModelProperty(name = "성별", required = true)
    private String gender;
    @ApiModelProperty(name = "나이", required = true)
    private int age;
    @ApiModelProperty(name = "멤버십", required = true)
    private boolean membership;

}

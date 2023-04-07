package com.ssafy.tlens.api.response;


import com.ssafy.tlens.entity.rdbms.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRespDto {
    private Long userId;
    private String email;
//    private String createdAt;

    public LoginRespDto(User user) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
//        this.createdAt = CustomDateUtil.toStringFormat(user.getCreatedAt());
    }
}
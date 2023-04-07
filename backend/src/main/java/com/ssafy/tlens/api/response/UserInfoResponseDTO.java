package com.ssafy.tlens.api.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.tlens.entity.rdbms.News;
import com.ssafy.tlens.entity.rdbms.User;
import lombok.*;

import javax.persistence.Column;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfoResponseDTO {

    private String email;
    private String nickname;
    private String gender;
    private int age;
    private boolean membership;

    public UserInfoResponseDTO(User user) {
        email = user.getEmail();
        nickname = user.getNickname();
        gender = user.getGender();
        age = user.getAge();
        membership = user.isMembership();
    }
}

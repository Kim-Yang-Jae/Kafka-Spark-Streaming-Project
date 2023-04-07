package com.ssafy.tlens.config.jwt;
import lombok.Getter;

@Getter
public class Subject {

    private final Long userId;

    private final String email;

//    private final String nickname;

    private final String type;

    private Subject(Long userId, String email, String type) {
        this.userId = userId;
        this.email = email;
//        this.nickname = nickname;
        this.type = type;
    }

    public static Subject atk(Long accountId, String email, String nickname) {
        return new Subject(accountId, email, "ATK");
    }

    public static Subject rtk(Long accountId, String email, String nickname) {
        return new Subject(accountId, email, "RTK");
    }
}
package com.ssafy.tlens.entity.rdbms;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "email", nullable = false, length = 40)
    private String email;

    @Column(name = "password", nullable = false, length = 100)
    @JsonIgnore
    @ToString.Exclude
    private String password;

    @Column(name = "nickname", nullable = false, length = 15)
    private String nickname;

    @Column(name = "gender", nullable = false, length = 15)
    private String gender; // MALE, FEMALE

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "membership", nullable = false)
    private boolean membership;

    @OneToMany(mappedBy = "user")
    private List<Keyword> keywords = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Subscribe> subscribes = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Scrap> scraps = new ArrayList<>();
}

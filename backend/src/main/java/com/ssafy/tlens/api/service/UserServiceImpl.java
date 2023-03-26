package com.ssafy.tlens.api.service;

import com.ssafy.tlens.dto.SignUpRequestDto;
import com.ssafy.tlens.entity.rdbms.User;
import com.ssafy.tlens.enums.ResponseEnum;
import com.ssafy.tlens.handler.exception.CustomApiException;
import com.ssafy.tlens.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component("userService")
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl (
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void signUp(SignUpRequestDto signUpRequestDto){
//        boolean isExist = userRepository
//                .existsByEmail(signUpRequestDto.getEmail());
//        if (isExist) throw new Exception("이미 존재하는 이메일입니다.");

        try {
            String encodedPassword = passwordEncoder.encode(signUpRequestDto.getPassword());

            User newUser;
            newUser = User.builder()
                    .email(signUpRequestDto.getEmail())
//                    .password(encodedPassword) //배포 DB에서 User entity 변경되면 다시 주석 풀 것
                    .password(signUpRequestDto.getPassword())
                    .nickname(signUpRequestDto.getNickname())
                    .gender(signUpRequestDto.getGender())
                    .age(signUpRequestDto.getAge())
                    .build();
            User u = userRepository.save(newUser);

        }catch (Exception e){
            throw new CustomApiException(ResponseEnum.USER_JOIN_FAIL);
        }
    }
}

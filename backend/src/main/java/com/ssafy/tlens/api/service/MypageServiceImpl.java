package com.ssafy.tlens.api.service;

import com.ssafy.tlens.api.response.NewsInfoDTO;
import com.ssafy.tlens.api.response.UserInfoResponseDTO;
import com.ssafy.tlens.common.exception.handler.NotFoundException;
import com.ssafy.tlens.entity.rdbms.Keyword;
import com.ssafy.tlens.entity.rdbms.User;
import com.ssafy.tlens.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MypageServiceImpl implements MypageService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserInfoResponseDTO getUserInfo(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Could not found user id : " + id));

        return new UserInfoResponseDTO(user);
    }

    @Override
    @Transactional
    public List<String> getUserKeyword(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Could not found user id : " + id));

        List<Keyword> keywordList = user.getKeywords();

        List<String> keywordNameList = keywordList.stream()
                .map(keyword -> keyword.getName())
                .collect(Collectors.toList());

        return keywordNameList;
    }

}

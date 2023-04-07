package com.ssafy.tlens.api.response;

import com.ssafy.tlens.api.service.SubscribeService;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class SubscribeInfoResponseDTO {

    private List<ArrayList<Integer>> ageCount;

    public SubscribeInfoResponseDTO(List<ArrayList<Integer>> ageCount) {
        this.ageCount = ageCount;
    }

}

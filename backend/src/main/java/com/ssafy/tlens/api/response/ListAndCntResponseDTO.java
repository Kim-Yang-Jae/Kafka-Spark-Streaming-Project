package com.ssafy.tlens.api.response;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ListAndCntResponseDTO {

    private List lst;
    private int cnt;
}

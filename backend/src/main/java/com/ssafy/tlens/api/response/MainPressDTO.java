package com.ssafy.tlens.api.response;

import com.ssafy.tlens.entity.rdbms.Press;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MainPressDTO {

    private Long pressId;
    private String thumbnail;

    public MainPressDTO(Press press) {
        this.pressId = press.getPressId();
        this.thumbnail = press.getThumbnail();
    }
}

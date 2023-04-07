package com.ssafy.tlens.api.response;

import com.ssafy.tlens.entity.rdbms.Enterprise;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;


@Getter
@NoArgsConstructor
public class EnterpriseDetailDTO {

    private Long enterpriseId;
    private String type;
    private String ceo;
    private String birthday;
    private String thumbnail;

    public EnterpriseDetailDTO(Enterprise enterprise) {
        this.enterpriseId = enterprise.getEnterpriseId();
        this.type = enterprise.getType();
        this.ceo = enterprise.getCeo();
        this.birthday = enterprise.getBirthday();
        this.thumbnail = enterprise.getThumbnail();
    }
}

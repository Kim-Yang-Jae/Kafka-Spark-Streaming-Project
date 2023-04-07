package com.ssafy.tlens.api.response;

import com.ssafy.tlens.entity.rdbms.Enterprise;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;

@Getter
@NoArgsConstructor
public class MainEnterpriseDTO {

    private Long enterpriseId;
    private String type;
    private String ceo;
    private String thumbnail;

    public MainEnterpriseDTO(Enterprise enterprise) {
        this.enterpriseId = enterprise.getEnterpriseId();
        this.type = enterprise.getType();
        this.ceo = enterprise.getCeo();
        this.thumbnail = enterprise.getThumbnail();
    }
}

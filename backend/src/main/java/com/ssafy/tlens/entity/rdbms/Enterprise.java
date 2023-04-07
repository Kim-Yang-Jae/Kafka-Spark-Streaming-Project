package com.ssafy.tlens.entity.rdbms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enterprise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enterprise_id")
    private Long enterpriseId;

    @Column(name = "type", length = 50)
    private String type;

    @Column(name = "birthday")
    private String birthday;

    @Column(name = "ceo", length = 50)
    private String ceo;

    @Column(name = "thumbnail", length= 300)
    private String thumbnail;

    @Builder.Default
    @OneToMany(mappedBy = "enterprise")
    private List<EnterpriseTrend> trends = new ArrayList<>();
}
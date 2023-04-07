package com.ssafy.tlens.entity.rdbms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.C;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Press {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "press_id")
    private Long pressId;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "thumbnail", length = 300)
    private String thumbnail;

    @Builder.Default
    @OneToMany(mappedBy = "press")
    private List<PressTrend> trends = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "press")
    private List<Reporter> reporters = new ArrayList<>();
}

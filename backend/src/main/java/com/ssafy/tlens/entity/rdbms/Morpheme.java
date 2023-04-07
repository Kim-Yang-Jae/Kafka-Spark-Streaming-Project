package com.ssafy.tlens.entity.rdbms;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class Morpheme {
    @Id
    @Column(name = "newsId")
    private String newsId;

    @Column(name = "title" , length = 200)
    @NotNull
    private String title;

    @Column(name = "word")
    private String word;

    @Column(name = "completed")
    @NotNull
    private Boolean completed;
}

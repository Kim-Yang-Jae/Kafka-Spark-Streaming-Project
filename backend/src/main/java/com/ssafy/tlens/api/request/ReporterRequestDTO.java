package com.ssafy.tlens.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReporterRequestDTO {
    String department; // 기자 분야
    String name; // 기자 성명
    String thumbNail; // 기자 프로필 사진
}

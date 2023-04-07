package com.ssafy.tlens.api.response;

import com.ssafy.tlens.entity.rdbms.Region;
import com.ssafy.tlens.entity.rdbms.RegionTrend;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegionResponseDTO {

    private Long regionId;
    private String regionName;
    private Long newsCnt;
    private String keywordName;
    private int keywordCnt;

    public RegionResponseDTO(Region region, Long newsCnt, String keyword, int cnt) {
        this.regionId = region.getRegionId();
        this.regionName = region.getName();
        this.newsCnt = newsCnt;
        this.keywordName = keyword;
        this.keywordCnt = cnt;
    }
}

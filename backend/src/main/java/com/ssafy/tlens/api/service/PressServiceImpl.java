package com.ssafy.tlens.api.service;

import com.ssafy.tlens.api.request.TrendRequestDTO;
import com.ssafy.tlens.api.response.MainPressDTO;
import com.ssafy.tlens.api.response.NewsInfoDTO;
import com.ssafy.tlens.common.exception.handler.NotFoundException;
import com.ssafy.tlens.entity.rdbms.Press;
import com.ssafy.tlens.entity.rdbms.PressTrend;
import com.ssafy.tlens.entity.rdbms.Reporter;
import com.ssafy.tlens.repository.PressRepository;
import com.ssafy.tlens.repository.PressTrendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PressServiceImpl implements PressService {

    private final PressTrendRepository pressTrendRepository;
    private final PressRepository pressRepository;

    // 언론사별 URL 코드번호 배열(네이버)
    private final String[][] press = {
            {
                    "032", // 경향신문
                    "081", // 서울신문
                    "028", // 한겨레
                    "005", // 국민일보
                    "022", // 세계일보
                    "469", // 한국일보
                    "020", // 동아일보
                    "023", // 조선일보
                    "021", // 문화일보
                    "025", // 중앙일보
                    "421", // 뉴스1
                    "449", // 채널A
                    "214", // MBC
                    "448", // TV조선
                    "003", // 뉴시스
                    "004", // 한국경제TV
                    "057", // MBN
                    "052", // YTN
                    "001", // 연합뉴스
                    "437", // JTBC
                    "055", // SBS
                    "422", // 연합뉴스TV
                    "056", // KBS
                    "374", // SBS Biz
            },
            {
                    "경향신문",
                    "서울신문",
                    "한겨레",
                    "국민일보",
                    "세계일보",
                    "한국일보",
                    "동아일보",
                    "조선일보",
                    "문화일보",
                    "중앙일보",
                    "뉴스1",
                    "채널A",
                    "MBC",
                    "TV조선",
                    "뉴시스",
                    "한국경제TV",
                    "MBN",
                    "YTN",
                    "연합뉴스",
                    "JTBC",
                    "SBS",
                    "연합뉴스TV",
                    "KBS",
                    "SBS Biz",
            },
            {
                    "https://mimgnews.pstatic.net/image/upload/office_logo/032/2020/09/15/logo_032_6_20200915155035.png", // 경향신문
                    "https://mimgnews.pstatic.net/image/upload/office_logo/081/2022/01/07/logo_081_6_20220107180811.png", // 서울신문
                    "https://mimgnews.pstatic.net/image/upload/office_logo/028/2020/09/15/logo_028_6_20200915190845.png", // 한겨레
                    "https://mimgnews.pstatic.net/image/upload/office_logo/005/2020/09/15/logo_005_6_20200915155137.png", // 국민일보
                    "https://mimgnews.pstatic.net/image/upload/office_logo/022/2020/09/15/logo_022_6_20200915183753.png", // 세계일보
                    "https://mimgnews.pstatic.net/image/upload/office_logo/469/2020/09/15/logo_469_6_20200915191039.png", // 한국일보
                    "https://mimgnews.pstatic.net/image/upload/office_logo/020/2019/01/22/logo_020_6_20190122142722.png", // 동아일보
                    "https://mimgnews.pstatic.net/image/upload/office_logo/023/2020/09/03/logo_023_6_20200903164340.png", // 조선일보
                    "https://mimgnews.pstatic.net/image/upload/office_logo/021/2022/08/04/logo_021_6_20220804125325.png", // 문화일보
                    "https://mimgnews.pstatic.net/image/upload/office_logo/025/2021/08/24/logo_025_6_20210824123340.png", // 중앙일보
                    "https://mimgnews.pstatic.net/image/upload/office_logo/421/2018/09/19/logo_421_6_20180919151119.png", // 뉴스1
                    "https://mimgnews.pstatic.net/image/upload/office_logo/449/2020/09/15/logo_449_6_20200915190621.png", // 채널A
                    "https://mimgnews.pstatic.net/image/upload/office_logo/214/2020/09/15/logo_214_6_20200915153641.png", // MBC
                    "https://mimgnews.pstatic.net/image/upload/office_logo/448/2020/09/15/logo_448_6_20200915154233.png", // TV조선
                    "https://mimgnews.pstatic.net/image/upload/office_logo/003/2019/01/23/logo_003_6_20190123191323.jpg", // 뉴시스
                    "https://mimgnews.pstatic.net/image/upload/office_logo/215/2020/09/15/logo_215_6_20200915191012.png", // 한국경제TV
                    "https://mimgnews.pstatic.net/image/upload/office_logo/057/2020/09/15/logo_057_6_20200915153924.png", // MBN
                    "https://mimgnews.pstatic.net/image/upload/office_logo/052/2020/11/17/logo_052_6_20201117112951.png", // YTN
                    "https://mimgnews.pstatic.net/image/upload/office_logo/001/2020/09/15/logo_001_6_20200915184213.png", // 연합뉴스
                    "https://mimgnews.pstatic.net/image/upload/office_logo/437/2018/09/19/logo_437_6_20180919153419.png", // JTBC
                    "https://mimgnews.pstatic.net/image/upload/office_logo/055/2020/09/15/logo_055_6_20200915154015.png", // SBS
                    "https://mimgnews.pstatic.net/image/upload/office_logo/422/2020/09/15/logo_422_6_20200915184242.png", // 연합뉴스TV
                    "https://mimgnews.pstatic.net/image/upload/office_logo/056/2020/09/15/logo_056_6_20200915153508.png", // KBS
                    "https://mimgnews.pstatic.net/image/upload/office_logo/374/2021/01/07/logo_374_6_20210107162903.png", // SBS Biz
            }
    };

    @Override
    @Transactional
    public void insertToPress(TrendRequestDTO request) {

        Press press = pressRepository.findById(request.getTargetId())
                .orElseThrow(() -> new NotFoundException("Could not found press id : " + request.getTargetId()));

        PressTrend pressTrend = PressTrend.builder()
                .keyword(request.getKeyword())
                .count(request.getCount())
                .date(request.getDate())
                .press(press)
                .build();

        pressTrendRepository.save(pressTrend);
    }

    @Override
    @Transactional
    public void updateToPress(TrendRequestDTO request) {

        PressTrend trend = pressTrendRepository.findById(request.getTargetId())
                .orElseThrow(() -> new NotFoundException("Could not found trend id : " + request.getTargetId()));

        trend.update(request.getKeyword(),request.getCount(),request.getDate());
    }

    @Override
    @Transactional
    public void deleteToPress(Long id) {

        PressTrend trend = pressTrendRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Could not found trend id : " + id));

        pressTrendRepository.delete(trend);
    }

    @Override
    public List<MainPressDTO> getMainPress() {

        List<Press> presses = pressRepository.findAll();

        List<MainPressDTO> pressInfoList = presses.stream()
                .map(press -> new MainPressDTO(press))
                .collect(Collectors.toList());

        return pressInfoList;
    }

    @Transactional
    @Override
    public void settingPress() {
        for(int i=0; i<press[0].length; i++){
            Press pressDto = Press.builder()
//                    .pressId(new Long(1))
                    .name(press[1][i])
                    .thumbnail(press[2][i])
//                    .trends(new ArrayList<PressTrend>())
//                    .reporters(new ArrayList<Reporter>())
                    .build();
            pressRepository.save(pressDto);
        }
    }

    @Override
    public Press getPress(long pressId) {
        Press press = pressRepository.findById(new Long(pressId))
                .orElseThrow(() -> new NotFoundException("Could not found press id : " + pressId));
        return press;
    }
}

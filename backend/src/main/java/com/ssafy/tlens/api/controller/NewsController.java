package com.ssafy.tlens.api.controller;

import com.ssafy.tlens.api.crawler.SeleniumNewsCrawler;
import com.ssafy.tlens.api.crawler.SeleniumReporterCrawler;
import com.ssafy.tlens.api.request.NewsRequestDTO;
import com.ssafy.tlens.api.response.NewsInfoDTO;
import com.ssafy.tlens.api.response.ReporterInfoDTO;
import com.ssafy.tlens.api.service.NewsService;
import com.ssafy.tlens.api.service.PressService;
import com.ssafy.tlens.api.service.ReporterService;
import com.ssafy.tlens.common.ResponseDto;
import com.ssafy.tlens.common.exception.handler.NotFoundException;
import com.ssafy.tlens.common.model.response.HttpResponseEntity;
import com.ssafy.tlens.entity.rdbms.News;
import com.ssafy.tlens.enums.ResponseEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

import static com.ssafy.tlens.common.model.response.HttpResponseEntity.success;

@Slf4j
@RestController
@RequiredArgsConstructor
@EnableScheduling
@Configuration
public class NewsController {
    private final NewsService newsService;
    private final ReporterService reporterService;
    private final PressService pressService;

//    TV조선 -> JTBC -> SBS Biz
//    private static String[][] test = {{"437", "448", "374"},{"JTBC", "TV조선", "SBS Biz"}};

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
            }
    };

    private final String[][] reporterPress = {
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
                    "215", // 한국경제TV
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
            }
    };

    private final String[][] regionPress = {
            {
                    "654", // 강원도민일보
                    "657", // 대구MBC
                    "659", // 전주MBC
                    "087", // 강원일보
                    "656", // 대전일보
                    "655", // CJB청주방송
                    "666", // 경기일보
                    "661", // JIBS
                    "082", // 부산일보
                    "660", // KBC광주방송
            },
            {
                    "강원도민일보",
                    "대구MBC",
                    "전주MBC",
                    "강원일보",
                    "대전일보",
                    "CJB청주방송",
                    "경기일보",
                    "JIBS",
                    "부산일보",
                    "kbc광주방송",
            },
            {
                    "강원도",
                    "대구광역시",
                    "전라북도",
                    "강원도",
                    "대전광역시",
                    "충청북도",
                    "경기도",
                    "제주도",
                    "부산광역시",
                    "광주광역시"
            }
    };

    private String baseNewsCrawlURL = "https://news.naver.com/main/list.naver?mode=LPOD&mid=sec&oid=";
    private String baseReporterCrawlURL = "https://media.naver.com/journalists/whole?officeId=";

    // 매일 낮 12시마다 실시간 기자 크롤링을 수행한다.
    // @Scheduled(cron = "0 0 12 * * ?")
    @PostMapping("reporterCrawling")
    public HttpResponseEntity.ResponseResult<?> reporterCrawling() throws Exception {
        for(int i=0; i<reporterPress[0].length; i++) {
            // 서비스에서 각 언론사별 전체 기자리스트를 획득한다.
            List<ReporterInfoDTO> reporterList;
            try {
                reporterList = reporterService.getReportersByPress(new Long(i+1));
            } catch(Exception e){
                reporterList = null;
            }
            // 크롤러는 1개의 언론사를 대상으로 크롤링을 수행한다.
            SeleniumReporterCrawler sc = new SeleniumReporterCrawler(pressService.getPress(new Long(i+1)), baseReporterCrawlURL + reporterPress[0][i], reporterList);

            // 크롤러는 1개의 언론사 소속의 기자 중, 새롭게 발견한 기자들만 추가한다.
            // 퇴사한 기자들은 추적하기 어렵기 때문에 RDBMS상에 남겨두는 것으로 한다.
            // 크롤러는 전체 크롤링을 수행하면서 크롤링한 기자 정보와 RDBMS상의 전체 기자 리스트를 비교한다.
            List<ReporterInfoDTO> list = sc.dynamicCrawling();
            int duplicate = 0;

            // 크롤링한 기자 정보를 RDBMS에 등록한다.
            for(int j=0; j<list.size(); j++){
                try{
                    reporterService.insert(list.get(j), pressService.getPress(new Long(i+1)));
                } catch(Exception e){
                    duplicate++;
                    System.out.println("["+ press[1][i] + "]" + " 동시성 문제로 중복된 기자 정보는 등록하지 않습니다.");
                    continue;
                }
            }
            System.out.println(reporterPress[1][i]+"의 기자정보 "+(list.size()-duplicate)+"건을 RDBMS reporter 테이블에 적재했습니다.");
        }
        return success();
    }

    // 5분마다 실시간 기사 크롤링을 수행한다.
//    @Scheduled(cron = "*/5 * * * * *")
    @PostMapping("newsCrawling")
    public HttpResponseEntity.ResponseResult<?> newsCrawling() throws Exception {
        // 전체 언론사를 대상으로 최근 뉴스 기사를 크롤링한다.
        for(int i=0; i<press[0].length; i++){
            // RDBMS상에서 존재하는 기등록된 기사와 크롤링된 기사의 작성시점은 크롤러에서 비교한다.
            News registNews = null;

            // RDBMS상에서 해당 언론사에 대해 기등록된 기사가 없을 경우, 샘플 데이터와의 비교를 통해 모든 데이터를 등록한다.
            try{
                registNews = newsService.getRecentData(press[1][i]);
                // System.out.println("○ "+registNews);
            } catch(NotFoundException e){
                registNews = News.builder()
                        .title("Compare Data")
                        .crawlLink("Compare Data")
                        .createdDate(LocalDateTime.of(1900,1,1,0,0))
                        .modifiedDate(LocalDateTime.of(1900,1,1,0,0))
                        .build();
            }

            // 크롤러는 1개의 언론사를 대상으로 크롤링을 수행한다.
            SeleniumNewsCrawler sc = new SeleniumNewsCrawler(press[1][i], baseNewsCrawlURL + press[0][i], registNews);

            // 크롤러는 1개의 언론사에서 작성된 전체 최근 기사 목록을 반환한다.
            // 크롤러는 크롤링한 기사와 RDBMS상의 최근 기사의 작성일자를 비교하여 크롤링 진행 여부를 결정한다.
            List<NewsRequestDTO> list = sc.dynamicCrawling();
            int duplicate = 0;

            // 크롤링한 기사를 RDBMS에 등록한다.
            for(int j=0; j<list.size(); j++){
                try{
                    newsService.insert(list.get(j));

                    // 크롤링한 기사의 제목 및 내용을 JSON 객체에 담아 Kafka 서버에 POST 요청으로 전달한다.
                    URL url = new URL("http://j8c206.p.ssafy.io:8090/kafka/data");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Content-Type", "application/json");

                    // 크롤링한 기사를 JSON 객체로 변환하여 Kafka에 전송한다.
                    JSONObject json = new JSONObject();
                    json.put("newsId", list.get(j).getCrawlLink());
                    json.put("title", list.get(j).getTitle());
                    json.put("content", list.get(j).getContent());

                    String requestBody = json.toString();
                    con.setDoOutput(true);
                    OutputStream os = con.getOutputStream();
                    os.write(requestBody.getBytes());
                    os.flush();
                    os.close();

                    int responseCode = con.getResponseCode();
                    InputStream is = (responseCode >= 200 && responseCode < 300) ? con.getInputStream() : con.getErrorStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String line;
                    StringBuilder response = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                    br.close();
                    con.disconnect();
                    System.out.println(response);
                } catch(Exception e){
                    duplicate++;
                    System.out.println("[" + press[1][i] + "]" + " 동시성 문제로 중복된 기사는 등록하지 않습니다.");
                    continue;
                }
            }
            System.out.println(press[1][i]+"의 최신기사 "+(list.size()-duplicate)+"건을 RDBMS news 테이블에 적재했습니다.");
        }
        return success();
    }

    // 5분마다 실시간 기사 크롤링을 수행한다.
     @Scheduled(cron = "*/5 * * * * *")
//    @PostMapping("regionCrawling")
    public HttpResponseEntity.ResponseResult<?> regionCrawling() throws Exception {
        // 지역 언론사를 대상으로 최근 뉴스 기사를 크롤링한다.
        for(int i=0; i<regionPress[0].length; i++){
            // RDBMS상에서 존재하는 기등록된 기사와 크롤링된 기사의 작성시점은 크롤러에서 비교한다.
            News registNews = null;

            // RDBMS상에서 해당 언론사에 대해 기등록된 기사가 없을 경우, 샘플 데이터와의 비교를 통해 모든 데이터를 등록한다.
            try{
                registNews = newsService.getRecentData(regionPress[1][i]);
                // System.out.println("○ "+registNews);
            } catch(NotFoundException e){
                registNews = News.builder()
                        .title("Compare Data")
                        .crawlLink("Compare Data")
                        .createdDate(LocalDateTime.of(1900,1,1,0,0))
                        .modifiedDate(LocalDateTime.of(1900,1,1,0,0))
                        .build();
            }

            // 크롤러는 1개의 언론사를 대상으로 크롤링을 수행한다.
            SeleniumNewsCrawler sc = new SeleniumNewsCrawler(regionPress[1][i], baseNewsCrawlURL + regionPress[0][i], registNews);

            // 크롤러는 1개의 언론사에서 작성된 전체 최근 기사 목록을 반환한다.
            // 크롤러는 크롤링한 기사와 RDBMS상의 최근 기사의 작성일자를 비교하여 크롤링 진행 여부를 결정한다.
            List<NewsRequestDTO> list = sc.dynamicCrawling();
            int duplicate = 0;

            // 크롤링한 기사를 RDBMS에 등록한다.
            for(int j=0; j<list.size(); j++){
                try{
                    // 해당 기사의 지역 컬럼에 지역을 추가한다.
                    list.get(j).setRegion(regionPress[2][i]);
                    newsService.insert(list.get(j));

                    // 크롤링한 기사의 제목 및 내용을 JSON 객체에 담아 Kafka 서버에 POST 요청으로 전달한다.
                    URL url = new URL("http://j8c206.p.ssafy.io:8090/kafka/data");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Content-Type", "application/json");

                    // 크롤링한 기사를 JSON 객체로 변환하여 Kafka에 전송한다.
                    JSONObject json = new JSONObject();
                    json.put("newsId", list.get(j).getCrawlLink());
                    json.put("title", list.get(j).getTitle());
                    json.put("content", list.get(j).getContent());

                    String requestBody = json.toString();
                    con.setDoOutput(true);
                    OutputStream os = con.getOutputStream();
                    os.write(requestBody.getBytes());
                    os.flush();
                    os.close();

                    int responseCode = con.getResponseCode();
                    InputStream is = (responseCode >= 200 && responseCode < 300) ? con.getInputStream() : con.getErrorStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String line;
                    StringBuilder response = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                    br.close();
                    con.disconnect();
                    System.out.println(response);
                } catch(Exception e){
                    duplicate++;
                    System.out.println("[" + regionPress[1][i] + "]" + " 동시성 문제로 중복된 기사는 등록하지 않습니다.");
                    continue;
                }
            }
            System.out.println(regionPress[1][i]+"의 최신기사 "+(list.size()-duplicate)+"건을 RDBMS news 테이블에 적재했습니다.");
        }
        return success();
    }

    @GetMapping("api/v1/news/search")
    public ResponseEntity<?> getNewsBySearch(@RequestParam String searchword, @RequestParam int pageNo, @RequestParam int pageSize) {
        List<NewsInfoDTO> result = newsService.getNewsBySearch(searchword, pageNo, pageSize);

        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.PRODUCT_SEARCH_SUCCESS, result), HttpStatus.OK);
    }

    @GetMapping("recent")
    public HttpResponseEntity.ResponseResult<?> recent(String press) throws Exception {
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(newsService.getRecentData(press).getCreatedDate());
        System.out.println(ldt.isAfter(newsService.getRecentData(press).getCreatedDate()));
        System.out.println(newsService.getRecentData(press).getCreatedDate().isAfter(ldt));

        return success();
    }

    @GetMapping("crawl")
    public HttpResponseEntity.ResponseResult<?> test() throws Exception {
        // 전체 언론사를 대상으로 최근 뉴스 기사를 크롤링한다.
        for(int i=0; i<press[0].length; i++){
            // RDBMS상에서 존재하는 기등록된 기사와 크롤링된 기사의 작성시점은 크롤러에서 비교한다.
            News registNews = null;

            // RDBMS상에서 해당 언론사에 대해 기등록된 기사가 없을 경우, 샘플 데이터와의 비교를 통해 모든 데이터를 등록한다.
            try{
                registNews = newsService.getRecentData(press[1][i]);
                // System.out.println("○ "+registNews);
            } catch(NotFoundException e){
                registNews = News.builder()
                        .title("Compare Data")
                        .crawlLink("Compare Data")
                        .createdDate(LocalDateTime.of(1900,1,1,0,0))
                        .modifiedDate(LocalDateTime.of(1900,1,1,0,0))
                        .build();
            }

            // 크롤러는 1개의 언론사를 대상으로 크롤링을 수행한다.
            SeleniumNewsCrawler sc = new SeleniumNewsCrawler(press[1][i], baseNewsCrawlURL + press[0][i], registNews);

            // 크롤러는 1개의 언론사에서 작성된 전체 최근 기사 목록을 반환한다.
            // 크롤러는 크롤링한 기사와 RDBMS상의 최근 기사의 작성일자를 비교하여 크롤링 진행 여부를 결정한다.
            List<NewsRequestDTO> list = sc.dynamicCrawling();

            // 크롤링한 기사를 RDBMS에 등록한다.
            for(int j=0; j<list.size(); j++){
                try{
                    newsService.insert(list.get(j));
                } catch(Exception e){
                    System.out.println("["+press[0][i]+"] " + press[1][i] + " 동시성 문제로 중복된 기사는 등록하지 않습니다.");
                    continue;
                }
            }
        }
        return success();
    }
}

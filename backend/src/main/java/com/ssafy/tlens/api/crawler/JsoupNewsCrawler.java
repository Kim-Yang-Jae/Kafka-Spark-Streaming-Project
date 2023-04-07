package com.ssafy.tlens.api.crawler;//package com.ssafy.tlens.api.crawler;
//
//import com.ssafy.tlens.api.request.NewsCrawlRequestDTO;
//import com.ssafy.tlens.entity.rdbms.News;
//import org.jsoup.HttpStatusException;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.select.Elements;
//
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
//// 정적 크롤링(JSoup, 뉴스 정보) + 동적 크롤링(Selenium, 뉴스 썸네일(Lazy Loading))
//public class JsoupCrawler {
//    private String baseURL = "https://n.news.naver.com/mnews/article";
//
//    // 10개의 종합신문 및 14개의 방송사를 대상으로 크롤링을 수행한다.
//    private String[] press = {
//            "32", // 경향신문
//            "81", // 서울신문
//            "28", // 한겨레
//            "5", // 국민일보
//            "22", // 세계일보
//            "469", // 한국일보
//            "20", // 동아일보
//            "23", // 조선일보
//            "21", // 문화일보
//            "25", // 중앙일보
//            "421", // 뉴스1
//            "449", // 채널A
//            "214", // MBC
//            "448", // TV조선
//            "3", // 뉴시스
//            "4", // 한국경제TV
//            "57", // MBN
//            "52", // YTN
//            "1", // 연합뉴스
//            "437", // JTBC
//            "55", // SBS
//            "422", // 연합뉴스TV
//            "56", // KBS
//            "374", // SBS Biz
//    };
//
////    public static void main(String[] args) throws IOException{
////        for(int i=0; i<press.length; i++){
////            // 언론사 번호가 두자리인 경우, 추가적인 전처리를 수행한다.
////            if(press[i].length()==2){
////                press[i] = "0"+press[i];
////            }
////            //
////            newsCrawl(baseURL + "/"+press[i]+"/0000000001");
////        }
////    }
//
//    // URL에 대한 기사 1개를 대상으로 크롤링을 수행한다.
//    public NewsCrawlRequestDTO newsCrawl(String URL) throws IOException {
//        // STEP1. [JSoup] 크롤링할 뉴스 정보를 담을 객체를 생성한다.
//        NewsCrawlRequestDTO newsDto = new NewsCrawlRequestDTO();
//        Document doc;
//
//        // STEP2. [JSoup] 크롤링을 수행할 URL을 전달하여 HTTP 연결을 시도한다.
//        // HttpStatusException가 발생하지 않는 경우에만 크롤링을 수행한다.
//        // Ex. 페이지를 찾을 수 없습니다.
//        // Ex. 언론사 요청에 의해 삭제된 기사입니다.
//        // Ex. 이 언론사 기사는 뉴스제휴평가위원회 '뉴스 제휴 및 제재 심사 규정'에 따라 노출이 중단된 기사입니다.
//        try {
//            doc = Jsoup.connect(URL).get();
//        } catch(HttpStatusException e) {
//            System.out.println("☆ (" + URL + ")" + " 크롤링을 수행할 수 없는 링크입니다.");
//            return null;
//        }
//
//        // STEP2. [JSoup] <head>에서 언론사(press) 정보를 획득한다.
//        Elements contents = doc.select("head");
//        newsDto.setPress(contents.select("meta[name=twitter:creator]").attr("content"));
//        if(newsDto.getPress()==null){
//            return null;
//        }
//
//        // STEP3. [JSoup] <body>에서 기사 정보(cont)를 획득한다.
//        contents = doc.select("body");
//        newsDto.setTitle(contents.select(".media_end_head_headline").select("span").text());
//        newsDto.setReporter(contents.select(".media_end_head_journalist_box").select("em").text()
//                .replaceAll(" ","").replaceAll("기자",""));
//        newsDto.setCategory(contents.select(".media_end_categorize_item").text());
//        newsDto.setThumbNail(contents.select("#img1").attr("src"));
//        newsDto.setCont(contents.select("._article_content").text());
//        newsDto.setLink(contents.select(".media_end_head_origin_link").attr("href"));
//
//        // STEP4. 원문 링크가 제공되지 않는 기사는 더 이상 크롤링하지 않는다.
//        if(newsDto.getLink()==""){
//            System.out.println("☆ (" + URL + ")" + " 원문 링크가 제공되지 않는 기사입니다.");
//            return null;
//        }
//
//        // STEP5. [JSoup] 기사 작성일자 및 수정일자를 (yyyy-MM-dd HH:mm) 형식으로 변환한다.
//        // 2023.03.23. 오후 9:17 -> 2023-03-23 21:17
//        newsDto.setCreatedDate(transLocalDateTime(getLocalDateTime(contents.select("._ARTICLE_DATE_TIME").text())));
//        newsDto.setModifiedDate(transLocalDateTime(getLocalDateTime(contents.select("._ARTICLE_MODIFY_DATE_TIME").text())));
//        // 수정일자가 없는 경우, 작성일자로 대체한다.
//        if(contents.select("._ARTICLE_MODIFY_DATE_TIME").text().equals("")){
//            newsDto.setModifiedDate(newsDto.getCreatedDate());
//        }
//
//        // STEP6. [Selenium] <body>에서 썸네일 URL(thumbnail)을 획득한다.
//        SeleniumCrawler st = new SeleniumCrawler(URL);
//        newsDto.setThumbNail(st.thumbCrawl());
//
//        System.out.println("★ (" + URL + ") 크롤링이 완료되었습니다. " + newsDto);
//        return newsDto;
//    }
//
//    // 기사 원문에서 발췌한 작성일자 및 수정일자를 엔티티형식에 맞춰 변환한다.
//    public String getLocalDateTime(String data){
//        // 작성일자 혹은 수정일자가 존재하지 않는 경우 변환을 취소한다.
//        if(data.length()==0){
//            return null;
//        }
//        else{
//            int year, month, day, hour, minute;
//            year = Integer.parseInt(data.substring(0,4));
//            month = Integer.parseInt(data.substring(5,7));
//            day = Integer.parseInt(data.substring(8,10));
//            String dayNight = data.substring(12,14);
//
//            // Case1. 시간이 0시~9시 사이인 경우
//            if(data.length()==19){
//                hour = Integer.parseInt(data.substring(15,16));
//                minute = Integer.parseInt(data.substring(17,19));
//            }
//            // Case2. 시간이 10시~12시 사이인 경우
//            else if(data.length()==20){
//                hour = Integer.parseInt(data.substring(15,17));
//                minute = Integer.parseInt(data.substring(18,20));
//            }
//            else{
//                hour = 0;
//                minute = 0;
//            }
//            if(dayNight.equals("오후")){
//                if(hour != 12){
//                    hour += 12;
//                }
//            }
//            LocalDateTime ldt = LocalDateTime.of(
//                    year, month, day, hour, minute
//            );
//            String date = ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
//
//            return date;
//        }
//    }
//
//    // (String -> yyyy-MM-dd HH:mm) -> (LocalDateTime)
//    public LocalDateTime transLocalDateTime(String data){
//        int year, month, day, hour, minute;
//        year = Integer.parseInt(data.substring(0,4));
//        month = Integer.parseInt(data.substring(5,7));
//        day = Integer.parseInt(data.substring(8,10));
//        hour = Integer.parseInt(data.substring(11,13));
//        minute = Integer.parseInt(data.substring(14,16));
//
//        LocalDateTime ldt = LocalDateTime.of(
//                year, month, day, hour, minute
//        );
//        return ldt;
//    }
//}
package com.ssafy.tlens.api.crawler;

import com.ssafy.tlens.api.response.ReporterInfoDTO;
import com.ssafy.tlens.entity.rdbms.Press;
import com.ssafy.tlens.entity.rdbms.Reporter;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SeleniumReporterCrawler {
    // STEP1. 웹 드라이버(WebDriver) 객체 생성
    private final WebDriver driver;

    // STEP2. 드라이버 속성(Properties) 지정
    public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    public static final String WEB_DRIVER_PATH = "src/main/java/com/ssafy/tlens/api/crawler/selenium/chromedriver_win32/chromedriver.exe";

    // STEP3. 크롤링 할 URL 지정
    private final String baseURL;
    private Press press;
    private boolean flag = true;

    // STEP4. 크롤링한 기자들을 담을 리스트 객체 생성
    private List<ReporterInfoDTO> list = new ArrayList<>();
    private List<ReporterInfoDTO> reporterList;

    public SeleniumReporterCrawler(Press press, String URL, List<ReporterInfoDTO> reporterList) {
        super();
        this.press = press;
        this.reporterList = reporterList;
        baseURL = URL;

        // STEP5. 시스템 속성 설정(System Property SetUp)
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

        // STEP6. 드라이버 옵션 설정(Driver SetUp)
        // PageLoadStrategy.NORMAL을 통해 페이지 로드가 완료된 시점에서 크롤링을 수행한다.
        ChromeOptions options = new ChromeOptions();

        // 브라우저 실행을 백그라운드로 전환한다.
        options.addArguments("headless");

        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--log-level=3");
        options.addArguments("--disable-loging");
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        driver = new ChromeDriver(options);
    }

    public List<ReporterInfoDTO> dynamicCrawling(){
        try {
            // STEP7. oid 값에 각 언론사별 코드를 넣어준 다음, 각 언론사별 최신 기자 정보 페이지로 이동한다.
            driver.get(baseURL);
            List<WebElement> elements;

            // STEP8. 무한스크롤 형식의 페이지이므로, 한번 내린 페이지의 높이가 이전 페이지의 높이와 같을 때까지(페이지의 끝에 도달할 때까지) 내린다.
            JavascriptExecutor js = (JavascriptExecutor) driver;
            int reporterCount = Integer.parseInt(driver.findElement(By.cssSelector("span.journalist_list_head_count._journalist_count")).getText());
            int compareCount = reporterCount;
            int currentCount = 0;
            long lastHeight = (long) js.executeScript("return document.body.scrollHeight");

            while (true) {
                js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
                if(compareCount != 0){
                    if(compareCount > 30){
                        currentCount += 30;
                        compareCount -= 30;
                    }
                    else{
                        currentCount += compareCount;
                        compareCount = 0;
                    }
                }
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3000));
                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("ul.journalist_list_content > li:nth-child("+Integer.toString(currentCount)+")")));
                // System.out.println("currentCount : " + currentCount);

                long newHeight = (long) js.executeScript("return document.body.scrollHeight");
//                if (newHeight == lastHeight) {
//                    break;
//                }
                lastHeight = newHeight;
                if(compareCount==0){
                    break;
                }
            }

            elements = driver.findElements(By.cssSelector(".journalist_list_content > li"));
            int count = 1;
            for(WebElement element : elements){
                String name, crawlLink, thumbnail;
                flag = true;

                name = element.findElement(By.cssSelector("a.journalist_list_content_name")).getText();
                crawlLink = element.findElement(By.cssSelector("div.journalist_list_content_title > a")).getAttribute("href");
                try {
                    thumbnail = element.findElement(By.cssSelector("a.journalist_list_content_thumb > img")).getAttribute("src");
                } catch (Exception e){
                    thumbnail = null;
                }
                ReporterInfoDTO reporterInfoDTO = new ReporterInfoDTO(Reporter.builder()
                        .name(name)
                        .crawlLink(crawlLink)
                        .thumbnail(thumbnail)
                        .press(press)
                        .build()
                );
                for(int j=0; j<reporterList.size(); j++){
                    // 기자정보 링크가 동일하면 동일인물로 간주하고 RDBMS에 삽입하지 않는다. (동명이인의 경우도 고려)
                    if(reporterInfoDTO.getCrawlLink().equals(reporterList.get(j).getCrawlLink())){
                        flag = false;
                        break;
                    }
                }
                if(flag){
                    // System.out.println(reporterInfoDTO);
                    list.add(reporterInfoDTO);
                }
            }
            System.out.println(press.getName()+"의 RDBMS 데이터 조회가 완료되었습니다. ("+reporterList.size()+"건)");
            System.out.println(press.getName()+"에 대한 기자정보 크롤링이 완료되었습니다. ("+elements.size()+"건)");

//            if(elements.size()!= reporterCount){
//                System.out.println(press.getName()+"의 기자 정보 크롤링이 불완전하게 수행되어 다시 시도합니다.");
//                dynamicCrawling();
//                return null;
//            }
        } catch (Exception e) {
        e.printStackTrace();
        } finally {
            driver.close();
            driver.quit();
            return list;
        }
    }
}

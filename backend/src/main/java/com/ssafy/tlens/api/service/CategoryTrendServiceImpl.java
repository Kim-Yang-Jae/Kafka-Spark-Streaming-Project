package com.ssafy.tlens.api.service;

import com.ssafy.tlens.api.response.CategoryTrendResponseDTO;
import com.ssafy.tlens.entity.rdbms.Category;
import com.ssafy.tlens.entity.rdbms.CategoryTrend;
import com.ssafy.tlens.entity.rdbms.Morpheme;
import com.ssafy.tlens.entity.rdbms.News;
import com.ssafy.tlens.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryTrendServiceImpl implements CategoryTrendService {
    private final MorphemeRepository morphemeRepository;
    private final CategoryTrendRepository categoryTrendRepository;
    private final CategoryTrendCustom categoryTrendCustom;
    private final CategoryRepository categoryRepository;
    private final NewsRepository newsRepository;

    @Override
    @Scheduled(cron = "* 10 * * * *")
    public void countKeyword() {
        List<Morpheme> morphemes = morphemeRepository.findMorphemeByCompleted(false);
        for (Morpheme morpheme : morphemes) {
            String words = morpheme.getWord();
            String newsId = morpheme.getNewsId();
            Timestamp today = new Timestamp(new Date().getTime());
            Timestamp todayMidnight = getDate("today");
            Timestamp tomorrowMidnight = getDate("tomorrow");
            News news = newsRepository.getNewsByCrawlLink(newsId);
            Category category = categoryRepository.getCategoryByName(news.getCategory());
            StringTokenizer st = new StringTokenizer(words, ",");
            while (st.hasMoreTokens()) {
                String word = st.nextToken();
                CategoryTrend trends = categoryTrendCustom.getCategoryTrendByKeywordAndDateAndCategory(word, todayMidnight, tomorrowMidnight, category.getCategoryId());
                if (trends == null) {
                    categoryTrendRepository.save(CategoryTrend.builder()
                            .keyword(word)
                            .count(1)
                            .category(category)
                            .date(today)
                            .build());
                } else {
                    categoryTrendRepository.save(CategoryTrend.builder()
                            .trendId(trends.getTrendId())
                            .keyword(trends.getKeyword())
                            .count(trends.getCount() + 1)
                            .category(trends.getCategory())
                            .date(trends.getDate())
                            .build()
                    );
                }

            }

            morphemeRepository.save(morpheme.toBuilder().completed(true).build());
        }
    }

    @Override
    public List<CategoryTrendResponseDTO> getCategoryTrends(String category, int count) {
        Category c = null;
        if ("전체".equals(category)) {
            c = Category.builder()
                    .categoryId(0L)
                    .name("전체")
                    .build();
        } else {
            c = categoryRepository.getCategoryByName(category);
        }
        Timestamp endTime = new Timestamp(new Date().getTime());
        Timestamp startTime = new Timestamp(endTime.getTime());
        startTime.setHours(-11);
        List<CategoryTrend> categoryTrendList = categoryTrendCustom.getCategoryTrendByDateAndCategoryOrderByCount(startTime, endTime, c.getCategoryId(), count);

        List<CategoryTrendResponseDTO> categorys =  categoryTrendList.stream()
                .map(categoryTrend -> CategoryTrendResponseDTO.builder()
                        .text(categoryTrend.getKeyword())
                        .value(categoryTrend.getCount())
                        .build())
                .collect(Collectors.toList());

        StringBuilder compare = new StringBuilder();
        List<CategoryTrendResponseDTO> result = new ArrayList<>();
        for ( CategoryTrendResponseDTO categoryTrend : categorys) {
            if(!compare.toString().contains(categoryTrend.getText())){
                compare.append(categoryTrend.getText());
                result.add(categoryTrend);
            }
        }

        return  result;
    }

    private Timestamp getDate(String day) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Timestamp resultDate = new Timestamp(now.getTime());
        if ("today".equals(day)) {
            resultDate.setHours(0);
            resultDate.setMinutes(0);
            resultDate.setSeconds(0);
            resultDate.setNanos(0);
        } else if ("tomorrow".equals(day)) {
            resultDate.setHours(24);
            resultDate.setMinutes(0);
            resultDate.setSeconds(0);
            resultDate.setNanos(0);
        }
        return resultDate;
    }
}

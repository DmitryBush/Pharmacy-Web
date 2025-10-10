package com.bush.pharmacy_web_app.controllers.rest.news;

import com.bush.pharmacy_web_app.model.dto.news.NewsDto;
import com.bush.pharmacy_web_app.repository.news.filter.NewsFilter;
import com.bush.pharmacy_web_app.service.news.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/news")
@RequiredArgsConstructor
public class NewsRestController {
    private final NewsService newsService;

    @GetMapping
    public Page<NewsDto> getNewsByFilter(@PageableDefault(size = 15, sort = "creationTime",
                                                 direction = Sort.Direction.DESC) Pageable pageable,
                                         NewsFilter filter) {
        return newsService.findAllNewsByFilter(filter, pageable);
    }
}

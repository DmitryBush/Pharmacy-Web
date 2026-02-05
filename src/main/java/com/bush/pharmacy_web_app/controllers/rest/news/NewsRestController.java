package com.bush.pharmacy_web_app.controllers.rest.news;

import com.bush.pharmacy_web_app.model.dto.news.NewsCreateDto;
import com.bush.pharmacy_web_app.model.dto.news.NewsReadDto;
import com.bush.pharmacy_web_app.repository.news.filter.NewsFilter;
import com.bush.pharmacy_web_app.service.news.NewsService;
import com.bush.pharmacy_web_app.service.news.NewsTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/news")
@RequiredArgsConstructor
public class NewsRestController {
    private final NewsService newsService;
    private final NewsTypeService typesService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NewsReadDto> createNews(@ModelAttribute @Validated NewsCreateDto newsCreateDto) {
        return ResponseEntity.ok(newsService.createNews(newsCreateDto));
    }

    @GetMapping
    public Page<NewsReadDto> getNewsByFilter(@PageableDefault(size = 15, sort = "creationTime",
                                                 direction = Sort.Direction.DESC) Pageable pageable,
                                             NewsFilter filter) {
        return newsService.findAllNewsByFilter(filter, pageable);
    }

    @GetMapping("/types")
    public ResponseEntity<List<String>> getAllNewsTypes() {
        return ResponseEntity.ok(typesService.findAllNewsTypes());
    }
}

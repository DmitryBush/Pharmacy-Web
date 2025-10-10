package com.bush.pharmacy_web_app.controllers.rest.news;

import com.bush.pharmacy_web_app.service.news.NewsImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/news-images")
@RequiredArgsConstructor
public class NewsImageRestController {
    private final NewsImageService newsImageService;

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getImageById(@PathVariable Long id) {
        return ResponseEntity.ok(newsImageService.findImageById(id));
    }
}

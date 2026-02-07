package com.bush.pharmacy_web_app.controllers.rest.news;

import com.bush.pharmacy_web_app.model.dto.news.NewsImageDto;
import com.bush.pharmacy_web_app.service.news.NewsImageService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/news-images")
@RequiredArgsConstructor
public class NewsImageRestController {
    private final NewsImageService newsImageService;

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getImageById(@PathVariable Long id) {
        return ResponseEntity.ok(newsImageService.findImageById(id));
    }

    @PostMapping(value = "/upload/{slug}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NewsImageDto>> attachImageToNews(@PathVariable String slug,
                                                          @Validated @NotNull @RequestParam List<MultipartFile> images) {
        return ResponseEntity.ok(newsImageService.attachImageToNews(slug, images));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        newsImageService.deleteNewsImageById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

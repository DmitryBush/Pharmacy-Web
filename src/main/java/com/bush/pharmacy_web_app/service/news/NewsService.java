package com.bush.pharmacy_web_app.service.news;

import com.bush.pharmacy_web_app.model.dto.news.NewsCreateDto;
import com.bush.pharmacy_web_app.model.dto.news.NewsReadDto;
import com.bush.pharmacy_web_app.model.entity.news.News;
import com.bush.pharmacy_web_app.model.entity.news.NewsType;
import com.bush.pharmacy_web_app.repository.news.NewsRepository;
import com.bush.pharmacy_web_app.repository.news.filter.NewsFilter;
import com.bush.pharmacy_web_app.service.exception.StorageException;
import com.bush.pharmacy_web_app.service.news.mapper.NewsCreateMapper;
import com.bush.pharmacy_web_app.service.news.mapper.NewsReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsService {
    private final NewsRepository newsRepository;

    private final NewsReadMapper newsReadMapper;
    private final NewsCreateMapper newsCreateMapper;

    private final NewsImageService newsImageService;

    public Page<NewsReadDto> findAllNewsByFilter(NewsFilter filter, Pageable pageable) {
        return newsRepository.findAllByFilter(filter, pageable)
                .map(newsReadMapper::map);
    }

    @Transactional
    public NewsReadDto createNews(NewsCreateDto newsCreateDto) {
        News news = Optional.of(newsCreateDto)
                .map(newsCreateMapper::mapToNews)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        try {
            newsImageService.createNewsImage(newsCreateDto.images(), news);
            return Optional.of(news)
                    .map(newsRepository::save)
                    .map(newsReadMapper::map)
                    .orElseThrow();
        } catch (StorageException e) {
            newsImageService.deleteNewsImagesByNewsId(news.getId());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
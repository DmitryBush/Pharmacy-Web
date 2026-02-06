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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsService {
    private final NewsRepository newsRepository;

    private final NewsReadMapper newsReadMapper;
    private final NewsCreateMapper newsCreateMapper;

    private final NewsImageService newsImageService;
    private final NewsTypeService newsTypeService;

    public Page<NewsReadDto> findAllNewsByFilter(NewsFilter filter, Pageable pageable) {
        return newsRepository.findAllByFilter(filter, pageable)
                .map(newsReadMapper::map);
    }

    @Transactional
    public NewsReadDto createNews(NewsCreateDto newsCreateDto) {
        News news = newsCreateMapper.mapToNews(newsCreateDto);
        try {
            return Optional.ofNullable(newsCreateDto.images())
                    .map(images -> newsImageService.createNewsImage(images, news))
                    .or(() -> Optional.of(news))
                    .map(newsRepository::save)
                    .map(newsReadMapper::map)
                    .orElseThrow();
        } catch (StorageException e) {
            log.error(e.getMessage());
            newsImageService.deleteNewsImages(news);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public NewsReadDto getNewsBySlug(String slug) {
        return newsRepository.findBySlug(slug)
                .map(newsReadMapper::map)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public NewsReadDto updateNewsBySlug(String slug, NewsCreateDto createDto) {
        NewsType newsType = newsTypeService.getReferenceById(createDto.type());
        return Optional.ofNullable(slug)
                .flatMap(newsRepository::findBySlug)
                .map(news -> newsCreateMapper.updateNews(news, createDto, newsType))
                .map(newsRepository::saveAndFlush)
                .map(newsReadMapper::map)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public void deleteNewsBySlug(String slug) {
        newsRepository.findBySlug(slug)
                .ifPresentOrElse(news -> {
                    newsRepository.delete(news);
                    newsImageService.deleteNewsImages(news);
                }, () -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                });
    }
}
package com.bush.pharmacy_web_app.service.news;

import com.bush.pharmacy_web_app.model.dto.news.NewsDto;
import com.bush.pharmacy_web_app.repository.news.NewsRepository;
import com.bush.pharmacy_web_app.repository.news.filter.NewsFilter;
import com.bush.pharmacy_web_app.service.news.mapper.NewsReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;

    private final NewsReadMapper newsReadMapper;

    public Page<NewsDto> findAllNewsByFilter(NewsFilter filter, Pageable pageable) {
        return newsRepository.findAllByFilter(filter, pageable)
                .map(newsReadMapper::map);
    }
}
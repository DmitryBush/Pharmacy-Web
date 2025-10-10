package com.bush.pharmacy_web_app.service.news.mapper;

import com.bush.pharmacy_web_app.model.dto.news.NewsDto;
import com.bush.pharmacy_web_app.model.entity.news.News;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewsReadMapper implements DtoMapper<News, NewsDto> {
    private final NewsImageReadMapper imageReadMapper;
    @Override
    public NewsDto map(News obj) {
        return new NewsDto(obj.getCreationTime(), obj.getType()
                .getType(), obj.getTitle(), obj.getBody(), obj.getNewsImageList()
                .stream()
                .map(imageReadMapper::map)
                .toList());
    }
}

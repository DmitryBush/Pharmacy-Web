package com.bush.pharmacy_web_app.service.news.mapper;

import com.bush.pharmacy_web_app.model.dto.news.NewsImageDto;
import com.bush.pharmacy_web_app.model.dto.news.NewsReadDto;
import com.bush.pharmacy_web_app.model.entity.news.News;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewsReadMapper implements DtoMapper<News, NewsReadDto> {
    private final NewsImageReadMapper imageReadMapper;
    @Override
    public NewsReadDto map(News obj) {
        return new NewsReadDto(obj.getCreationTime(), obj.getType()
                .getType(), obj.getTitle(), obj.getSlug(), obj.getBody(), obj.getNewsImageList()
                .stream()
                .map(imageReadMapper::map)
                .toList());
    }

    public NewsReadDto map(News obj, List<NewsImageDto> newsImages) {
        return new NewsReadDto(obj.getCreationTime(), obj.getType().getType(), obj.getTitle(),
                obj.getSlug(), obj.getBody(), newsImages);
    }
}

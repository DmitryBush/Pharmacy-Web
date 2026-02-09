package com.bush.pharmacy_web_app.service.news.mapper;

import com.bush.pharmacy_web_app.model.dto.news.NewsImageDto;
import com.bush.pharmacy_web_app.model.entity.news.NewsImage;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
import org.springframework.stereotype.Component;

@Component
public class NewsImageReadMapper implements DtoMapper<NewsImage, NewsImageDto> {
    @Override
    public NewsImageDto map(NewsImage obj) {
        return new NewsImageDto(obj.getId());
    }
}

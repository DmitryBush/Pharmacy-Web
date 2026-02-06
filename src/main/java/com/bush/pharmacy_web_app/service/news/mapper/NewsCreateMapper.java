package com.bush.pharmacy_web_app.service.news.mapper;

import com.bush.pharmacy_web_app.model.dto.news.NewsCreateDto;
import com.bush.pharmacy_web_app.model.entity.news.News;
import com.bush.pharmacy_web_app.model.entity.news.NewsType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.time.ZonedDateTime;
import java.util.Optional;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = NewsTypeMapper.class, imports = ZonedDateTime.class)
public interface NewsCreateMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "newsImageList", ignore = true)
    @Mapping(target = "creationTime", expression = "java(ZonedDateTime.now())")
    News mapToNews(NewsCreateDto dto);

    default News updateNews(News news, NewsCreateDto newsCreateDto, NewsType newsType) {
        news.setCreationTime(ZonedDateTime.now());
        Optional.ofNullable(newsCreateDto.title())
                .ifPresent(news::setTitle);
        Optional.ofNullable(newsCreateDto.body())
                .ifPresent(news::setBody);
        Optional.ofNullable(newsType)
                .ifPresent(news::setType);
        return news;
    }
}

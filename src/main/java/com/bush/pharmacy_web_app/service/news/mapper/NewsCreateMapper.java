package com.bush.pharmacy_web_app.service.news.mapper;

import com.bush.pharmacy_web_app.model.dto.news.NewsCreateDto;
import com.bush.pharmacy_web_app.model.entity.news.News;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.time.ZonedDateTime;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = NewsTypeMapper.class, imports = ZonedDateTime.class)
public interface NewsCreateMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "newsImageList", ignore = true)
    @Mapping(target = "creationTime", expression = "java(ZonedDateTime.now())")
    News mapToNews(NewsCreateDto dto);
}

package com.bush.pharmacy_web_app.service.news.mapper;

import com.bush.pharmacy_web_app.model.entity.news.NewsType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NewsTypeMapper {
    NewsType mapToNewsType(String type);
}

package com.bush.pharmacy_web_app.service.news.mapper;

import com.bush.pharmacy_web_app.model.dto.news.NewsTypeDto;
import com.bush.pharmacy_web_app.model.entity.news.NewsType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NewsTypeMapper {
    @Mapping(target = "id", source = "typeId")
    @Mapping(target = "typeName", source = "type")
    NewsTypeDto mapToNewsTypeDto(NewsType type);
}

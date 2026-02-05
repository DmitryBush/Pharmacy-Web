package com.bush.pharmacy_web_app.service.news.mapper;

import com.bush.pharmacy_web_app.model.entity.news.News;
import com.bush.pharmacy_web_app.model.entity.news.NewsImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NewsImageCreateMapper {
    @Mapping(target = "news")
    @Mapping(target = "imageWorkPath", source = "multipartFile", qualifiedByName = "getFileName")
    NewsImage mapToNewsImage(MultipartFile multipartFile, News news);

    @Named("getFileName")
    default String getFileName(MultipartFile multipartFile) {
        return UUID.nameUUIDFromBytes(Objects.requireNonNull(multipartFile.getOriginalFilename()).getBytes())
                .toString();
    }
}

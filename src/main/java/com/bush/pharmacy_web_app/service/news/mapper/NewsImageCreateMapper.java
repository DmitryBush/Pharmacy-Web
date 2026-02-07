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
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "news")
    @Mapping(target = "imageLinkPath", expression = "java(getFilePath(multipartFile, news))")
    NewsImage mapToNewsImage(MultipartFile multipartFile, News news);

    @Named("getFilePath")
    default String getFilePath(MultipartFile multipartFile, News news) {
        byte[] byteNewsSlug = news.getSlug().getBytes();
        byte[] byteFilename = Objects.requireNonNull(multipartFile.getOriginalFilename()).getBytes();
        return String.format("news/%s/%s", UUID.nameUUIDFromBytes(byteNewsSlug), UUID.nameUUIDFromBytes(byteFilename));
    }
}

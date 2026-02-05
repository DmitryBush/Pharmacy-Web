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
    @Mapping(target = "imageWorkPath", expression = "java(getFilePath(multipartFile, uniqueFolderName))")
    NewsImage mapToNewsImage(MultipartFile multipartFile, News news, String uniqueFolderName);

    @Named("getFilePath")
    default String getFilePath(MultipartFile multipartFile, String uniqueFolderName) {
        byte[] byteFilename = Objects.requireNonNull(multipartFile.getOriginalFilename()).getBytes();
        return String.format("%s/%s", uniqueFolderName, UUID.nameUUIDFromBytes(byteFilename));
    }
}

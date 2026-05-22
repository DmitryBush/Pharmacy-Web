package com.bush.pharmacy_web_app.model.dto.news;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.ZonedDateTime;
import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public record NewsReadDto(ZonedDateTime creationTime,
                          NewsTypeDto type,
                          String title,
                          String slug,
                          String body,
                          List<NewsImageDto> imageDtoList) {
}

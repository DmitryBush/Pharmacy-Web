package com.bush.pharmacy_web_app.model.dto.news;

import java.time.ZonedDateTime;
import java.util.List;

public record NewsReadDto(ZonedDateTime creationTime,
                          Short type,
                          String title,
                          String slug,
                          String body,
                          List<NewsImageDto> imageDtoList) {
}

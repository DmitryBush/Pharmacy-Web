package com.bush.pharmacy_web_app.model.dto.news;

import java.time.ZonedDateTime;
import java.util.List;

public record NewsDto(ZonedDateTime creationTime,
                      String type,
                      String title,
                      String body,
                      List<NewsImageDto> imageDtoList) {
}

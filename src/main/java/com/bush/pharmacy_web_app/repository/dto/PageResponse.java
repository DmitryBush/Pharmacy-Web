package com.bush.pharmacy_web_app.repository.dto;

import lombok.Value;
import org.springframework.data.domain.Page;

import java.util.List;

@Value
public class PageResponse<T> {
    Metadata metadata;
    List<T> content;

    public record Metadata(int number,
                           int size,
                           long totalElementCount) { }

    public static <T> PageResponse<T> of(Page<T> page) {
        return new PageResponse<>(
                new Metadata(page.getNumber(), page.getSize(),
                        page.getTotalElements()), page.getContent());
    }
}

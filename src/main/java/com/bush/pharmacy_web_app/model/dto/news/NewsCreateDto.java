package com.bush.pharmacy_web_app.model.dto.news;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record NewsCreateDto(@NotBlank @Length(max = 255) String title,
                            @NotBlank @Length(max = 64) String slug,
                            @NotBlank @Length(max = 25) String type,
                            @NotNull String body,
                            List<MultipartFile> images) {
}

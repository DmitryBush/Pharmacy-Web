package com.bush.pharmacy_web_app.model.dto.news;

import com.bush.pharmacy_web_app.validation.Slug;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record NewsCreateDto(@NotBlank @Length(max = 255) String title,
                            @NotBlank @Slug @Length(max = 64) String slug,
                            @NotNull Short type,
                            @NotNull String body) {
}

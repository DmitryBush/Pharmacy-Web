package com.bush.pharmacy_web_app.repository.dto.orders;

import com.bush.pharmacy_web_app.validation.MobilePhone;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CustomerCreateDto(@NotBlank @Length(min = 2, max = 25) String name,
                                @NotBlank @Length(min = 2, max = 25) String surname,
                                @Length(max = 25) String lastName,
                                @NotBlank @MobilePhone String mobilePhone,
                                @NotBlank @Length(min = 4, max = 256) String password) {
}

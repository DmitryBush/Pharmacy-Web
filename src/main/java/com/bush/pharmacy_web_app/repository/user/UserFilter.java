package com.bush.pharmacy_web_app.repository.user;

import jakarta.validation.constraints.NotNull;

public record UserFilter(@NotNull String mobilePhone, @NotNull String role) {
}

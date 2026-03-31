package com.bush.pharmacy_web_app.repository.user;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UserFilter(@NotNull String mobilePhone, @NotNull List<String> role) {
}

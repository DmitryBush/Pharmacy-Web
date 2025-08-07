package com.bush.pharmacy_web_app.model.dto.security;

public record CsrfDto(String headerName, String token) {
}

package com.bush.pharmacy_web_app.model.dto.user;

public record AdminUserReadDto(String mobilePhone,
                               String name,
                               String surname,
                               String lastName,
                               String role) {
}

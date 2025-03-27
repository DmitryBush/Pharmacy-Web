package com.bush.pharmacy_web_app.repository.dto;

public record CustomerCreateDto(String name,
                                String surname,
                                String lastName,
                                String mobilePhone,
                                String password) {
}

package com.bush.pharmacy_web_app.repository.dto.orders;

public record CustomerCreateDto(String name,
                                String surname,
                                String lastName,
                                String mobilePhone,
                                String password) {
}

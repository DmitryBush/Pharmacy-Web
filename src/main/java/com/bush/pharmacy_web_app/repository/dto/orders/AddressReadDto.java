package com.bush.pharmacy_web_app.repository.dto.orders;

public record AddressReadDto(String subject,
                             String district,
                             String settlement,
                             String street,
                             String house,
                             String apartment,
                             String postalCode) {
}

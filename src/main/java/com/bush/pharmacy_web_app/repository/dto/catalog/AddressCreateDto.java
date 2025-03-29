package com.bush.pharmacy_web_app.repository.dto.catalog;

import lombok.*;

import java.util.Optional;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class AddressCreateDto {
    private String subject;
    private String district;
    private String settlement;
    private String street;
    private String house;
    private String apartment;
    private String postalCode;

    public AddressCreateDto(String subject, String settlement, String street,
                            String house, String apartment, String postalCode) {
        this.subject = subject;
        this.settlement = settlement;
        this.street = street;
        this.house = house;
        this.apartment = apartment;
        this.postalCode = postalCode;
    }
}

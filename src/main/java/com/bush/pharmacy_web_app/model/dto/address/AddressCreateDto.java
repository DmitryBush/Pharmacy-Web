package com.bush.pharmacy_web_app.model.dto.address;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressCreateDto {
    private Long id;
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

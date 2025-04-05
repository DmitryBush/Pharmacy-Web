package com.bush.pharmacy_web_app.repository.dto.manufacturer;

public record ManufacturerCreateDto(String name,
                                    CountryCreateDto country) {
}

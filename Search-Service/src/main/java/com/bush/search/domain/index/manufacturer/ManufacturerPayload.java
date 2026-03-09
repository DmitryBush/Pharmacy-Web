package com.bush.search.domain.index.manufacturer;

public record ManufacturerPayload(Long id,
                                  String name,
                                  CountryPayload country) {
}

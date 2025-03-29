package com.bush.pharmacy_web_app.repository.dto.catalog;

public record SupplierCreateDto(String itn,
                                String name,
                                AddressCreateDto address) {
}

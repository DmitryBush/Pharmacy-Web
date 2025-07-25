package com.bush.pharmacy_web_app.model.dto.supplier;

import com.bush.pharmacy_web_app.model.dto.address.AddressReadDto;

public record SupplierReadDto(String itn,
                              String name,
                              AddressReadDto address) {
}

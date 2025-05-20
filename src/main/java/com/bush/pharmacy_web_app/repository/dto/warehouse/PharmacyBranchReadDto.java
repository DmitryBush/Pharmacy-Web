package com.bush.pharmacy_web_app.repository.dto.warehouse;

import com.bush.pharmacy_web_app.repository.dto.orders.AddressReadDto;

import java.util.List;

public record PharmacyBranchReadDto(Long id,
                                    String name,
                                    AddressReadDto address,
                                    Integer warehouseLimitations) {
}

package com.bush.pharmacy_web_app.repository.dto.orders;

import com.bush.pharmacy_web_app.repository.dto.card.StorageItemsReadDto;

import java.util.List;

public record PharmacyBranchReadDto(AddressReadDto address,
                                    List<StorageItemsReadDto> items) {
}

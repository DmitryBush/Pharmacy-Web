package com.bush.pharmacy_web_app.repository.dto.card;

import com.bush.pharmacy_web_app.repository.dto.orders.MedicineReadDto;

public record StorageItemsReadDto(Long id,
                                  Integer amount,
                                  MedicineReadDto medicine) {
}

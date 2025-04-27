package com.bush.pharmacy_web_app.repository.dto.card;

import com.bush.pharmacy_web_app.repository.dto.medicine.MedicineReadDto;

public record StorageItemsReadDto(Long id,
                                  Integer amount,
                                  MedicineReadDto medicine) {
}

package com.bush.pharmacy_web_app.repository.dto.card;

import com.bush.pharmacy_web_app.repository.dto.medicine.MedicinePreviewReadDto;

public record StorageItemsReadDto(Long id,
                                  Integer amount,
                                  MedicinePreviewReadDto medicine) {
}

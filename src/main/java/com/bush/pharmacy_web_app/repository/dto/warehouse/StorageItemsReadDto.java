package com.bush.pharmacy_web_app.repository.dto.warehouse;

import com.bush.pharmacy_web_app.repository.dto.medicine.MedicinePreviewReadDto;

public record StorageItemsReadDto(Long id,
                                  Integer amount,
                                  MedicinePreviewReadDto medicine) {
}

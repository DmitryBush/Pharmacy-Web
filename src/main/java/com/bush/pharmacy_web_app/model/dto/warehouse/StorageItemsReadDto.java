package com.bush.pharmacy_web_app.model.dto.warehouse;

import com.bush.pharmacy_web_app.model.dto.medicine.MedicinePreviewReadDto;

public record StorageItemsReadDto(Long id,
                                  Integer amount,
                                  MedicinePreviewReadDto medicine) {
}

package com.bush.pharmacy_web_app.model.dto.warehouse;

import com.bush.pharmacy_web_app.model.dto.medicine.MedicinePreviewReadDto;

import java.math.BigDecimal;

public record TransactionItemDto(MedicinePreviewReadDto medicine,
                                 Integer amount,
                                 BigDecimal price) {
}

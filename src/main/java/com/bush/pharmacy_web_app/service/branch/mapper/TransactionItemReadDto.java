package com.bush.pharmacy_web_app.service.branch.mapper;

import com.bush.pharmacy_web_app.model.dto.warehouse.TransactionItemDto;
import com.bush.pharmacy_web_app.model.entity.branch.transaction.TransactionItem;
import com.bush.pharmacy_web_app.service.medicine.mapper.MedicinePreviewReadMapper;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionItemReadDto implements DtoMapper<TransactionItem, TransactionItemDto> {
    private final MedicinePreviewReadMapper previewReadMapper;
    @Override
    public TransactionItemDto map(TransactionItem obj) {
        return new TransactionItemDto(previewReadMapper.map(obj.getId().getMedicine()), obj.getAmount(), obj.getPrice());
    }
}

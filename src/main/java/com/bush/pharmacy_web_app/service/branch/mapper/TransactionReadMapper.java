package com.bush.pharmacy_web_app.service.branch.mapper;

import com.bush.pharmacy_web_app.model.dto.warehouse.TransactionReadDto;
import com.bush.pharmacy_web_app.model.entity.branch.transaction.TransactionHistory;
import com.bush.pharmacy_web_app.service.medicine.mapper.MedicinePreviewReadMapper;
import com.bush.pharmacy_web_app.service.order.mapper.OrderReadMapper;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TransactionReadMapper implements DtoMapper<TransactionHistory, TransactionReadDto> {
    private final OrderReadMapper orderReadMapper;
    private final MedicinePreviewReadMapper previewReadMapper;
    @Override
    public TransactionReadDto map(TransactionHistory obj) {
        var order = Optional.ofNullable(obj.getOrder())
                .map(orderReadMapper::map)
                .orElse(null);
        var transactionItems = obj.getItems().stream().map(previewReadMapper::map).toList();

        return new TransactionReadDto(obj.getId(), obj.getCompletedAt(), obj.getType().getTransactionName(),
                order, transactionItems);
    }
}

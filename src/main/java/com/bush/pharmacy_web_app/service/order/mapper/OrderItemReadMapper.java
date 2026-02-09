package com.bush.pharmacy_web_app.service.order.mapper;

import com.bush.pharmacy_web_app.model.dto.orders.OrderItemReadDto;
import com.bush.pharmacy_web_app.model.entity.order.OrderItem;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
import com.bush.pharmacy_web_app.service.medicine.mapper.MedicinePreviewReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderItemReadMapper implements DtoMapper<OrderItem, OrderItemReadDto> {
    private final MedicinePreviewReadMapper medicinePreviewReadMapper;
    @Override
    public OrderItemReadDto map(OrderItem obj) {
        var medicine = Optional.ofNullable(obj.getProduct())
                .map(medicinePreviewReadMapper::map)
                .orElse(null);
        return new OrderItemReadDto(obj.getId(), obj.getAmount(), obj.getPrice(), medicine);
    }
}

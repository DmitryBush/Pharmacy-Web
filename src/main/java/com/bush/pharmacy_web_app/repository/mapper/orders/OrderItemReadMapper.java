package com.bush.pharmacy_web_app.repository.mapper.orders;

import com.bush.pharmacy_web_app.repository.dto.orders.OrderItemReadDto;
import com.bush.pharmacy_web_app.repository.entity.order.OrderItem;
import com.bush.pharmacy_web_app.repository.mapper.DtoMapper;
import com.bush.pharmacy_web_app.repository.mapper.medicine.MedicinePreviewReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderItemReadMapper implements DtoMapper<OrderItem, OrderItemReadDto> {
    private final MedicinePreviewReadMapper medicinePreviewReadMapper;
    @Override
    public OrderItemReadDto map(OrderItem obj) {
        var medicine = Optional.ofNullable(obj.getMedicine())
                .map(medicinePreviewReadMapper::map)
                .orElse(null);
        return new OrderItemReadDto(obj.getId(), obj.getAmount(), obj.getPrice(), medicine);
    }
}

package com.bush.pharmacy_web_app.repository.mapper.orders;

import com.bush.pharmacy_web_app.repository.dto.orders.OrderItemReadDto;
import com.bush.pharmacy_web_app.repository.entity.order.OrderItem;
import com.bush.pharmacy_web_app.repository.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderItemReadMapper implements DtoMapper<OrderItem, OrderItemReadDto> {
    private final MedicineReadMapper medicineReadMapper;
    @Override
    public OrderItemReadDto map(OrderItem obj) {
        var medicine = Optional.ofNullable(obj.getMedicine())
                .map(medicineReadMapper::map)
                .orElse(null);
        return new OrderItemReadDto(obj.getId(), obj.getAmount(), obj.getPrice(), medicine);
    }
}

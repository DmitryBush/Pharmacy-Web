package com.bush.pharmacy_web_app.repository.mapper.orders;

import com.bush.pharmacy_web_app.repository.dto.orders.OrderStatusDto;
import com.bush.pharmacy_web_app.repository.entity.order.OrderStatus;
import com.bush.pharmacy_web_app.repository.mapper.DtoMapper;
import com.bush.pharmacy_web_app.repository.mapper.orders.handler.AbstractStatusHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderStatusReadMapper implements DtoMapper<OrderStatus, OrderStatusDto> {
    @Autowired
    private final AbstractStatusHandler handler;

    @Override
    public OrderStatusDto map(OrderStatus obj) {
        return new OrderStatusDto(obj.ordinal(), handler.handle(obj));
    }
}

package com.bush.pharmacy_web_app.service.order.mapper;

import com.bush.pharmacy_web_app.model.dto.orders.OrderStatusDto;
import com.bush.pharmacy_web_app.model.entity.order.state.OrderState;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
import com.bush.pharmacy_web_app.service.order.mapper.handler.AbstractStatusHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderStatusReadMapper implements DtoMapper<OrderState, OrderStatusDto> {
    @Autowired
    private final AbstractStatusHandler handler;

    @Override
    public OrderStatusDto map(OrderState obj) {
        return new OrderStatusDto(obj.ordinal(), handler.handle(obj));
    }
}

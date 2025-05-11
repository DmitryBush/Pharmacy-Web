package com.bush.pharmacy_web_app.repository.mapper.orders;

import com.bush.pharmacy_web_app.repository.entity.order.OrderStatus;
import com.bush.pharmacy_web_app.repository.mapper.DtoMapper;
import com.bush.pharmacy_web_app.repository.mapper.orders.handler.AbstractStatusHandler;
import com.bush.pharmacy_web_app.repository.mapper.orders.handler.StatusHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderStatusReadMapper implements DtoMapper<OrderStatus, String> {
    @Autowired
    private final StatusHandler handler;

    @Override
    public String map(OrderStatus obj) {
        return handler.handle(obj);
    }
}

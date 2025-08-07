package com.bush.pharmacy_web_app.service.order.mapper.handler;

import com.bush.pharmacy_web_app.model.entity.order.state.OrderState;

public interface StatusHandler {
    StatusHandler setNext(StatusHandler handler);
    String handle(OrderState status);
}

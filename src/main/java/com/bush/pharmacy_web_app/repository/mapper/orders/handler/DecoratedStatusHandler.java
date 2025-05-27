package com.bush.pharmacy_web_app.repository.mapper.orders.handler;

import com.bush.pharmacy_web_app.repository.entity.order.OrderStatus;

public class DecoratedStatusHandler extends AbstractStatusHandler {
    public DecoratedStatusHandler() {
        super(OrderStatus.DECORATED);
    }

    @Override
    protected String getName() {
        return "Оформлен";
    }
}

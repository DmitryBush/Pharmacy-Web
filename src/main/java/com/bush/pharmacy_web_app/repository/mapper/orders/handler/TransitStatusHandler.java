package com.bush.pharmacy_web_app.repository.mapper.orders.handler;

import com.bush.pharmacy_web_app.repository.entity.order.OrderStatus;

public class TransitStatusHandler extends AbstractStatusHandler {
    public TransitStatusHandler() {
        super(OrderStatus.TRANSIT);
    }

    @Override
    protected String getName() {
        return "";
    }
}

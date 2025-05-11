package com.bush.pharmacy_web_app.repository.mapper.orders.handler;

import com.bush.pharmacy_web_app.repository.entity.order.OrderStatus;

public class CanceledStatusHandler extends AbstractStatusHandler {
    public CanceledStatusHandler() {
        super(OrderStatus.CANCELED);
    }

    @Override
    protected String getName() {
        return "Отменен";
    }
}

package com.bush.pharmacy_web_app.repository.mapper.orders.handler;

import com.bush.pharmacy_web_app.repository.entity.order.state.OrderStatus;

public class CompletedStatusHandler extends AbstractStatusHandler {
    public CompletedStatusHandler() {
        super(OrderStatus.COMPLETED);
    }

    @Override
    protected String getName() {
        return "Завершен";
    }
}

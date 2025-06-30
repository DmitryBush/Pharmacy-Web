package com.bush.pharmacy_web_app.repository.mapper.orders.handler;

import com.bush.pharmacy_web_app.repository.entity.order.state.OrderStatus;

public class DeferredStatusHandler extends AbstractStatusHandler {
    public DeferredStatusHandler() {
        super(OrderStatus.DEFERRED);
    }

    @Override
    protected String getName() {
        return "Отложен";
    }
}

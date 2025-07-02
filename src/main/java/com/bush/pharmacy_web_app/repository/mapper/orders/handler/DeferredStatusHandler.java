package com.bush.pharmacy_web_app.repository.mapper.orders.handler;

import com.bush.pharmacy_web_app.repository.entity.order.state.OrderState;

public class DeferredStatusHandler extends AbstractStatusHandler {
    public DeferredStatusHandler() {
        super(OrderState.DEFERRED);
    }

    @Override
    protected String getName() {
        return "Отложен";
    }
}

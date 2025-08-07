package com.bush.pharmacy_web_app.service.order.mapper.handler;

import com.bush.pharmacy_web_app.model.entity.order.state.OrderState;

public class DeferredStatusHandler extends AbstractStatusHandler {
    public DeferredStatusHandler() {
        super(OrderState.DEFERRED);
    }

    @Override
    protected String getName() {
        return "Отложен";
    }
}

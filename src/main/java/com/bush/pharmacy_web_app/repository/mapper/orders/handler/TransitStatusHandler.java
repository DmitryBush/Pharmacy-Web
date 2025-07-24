package com.bush.pharmacy_web_app.repository.mapper.orders.handler;

import com.bush.pharmacy_web_app.repository.entity.order.state.OrderState;

public class TransitStatusHandler extends AbstractStatusHandler {
    public TransitStatusHandler() {
        super(OrderState.TRANSIT);
    }

    @Override
    protected String getName() {
        return "";
    }
}

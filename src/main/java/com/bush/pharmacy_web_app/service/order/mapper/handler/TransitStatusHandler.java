package com.bush.pharmacy_web_app.service.order.mapper.handler;

import com.bush.pharmacy_web_app.model.entity.order.state.OrderState;

public class TransitStatusHandler extends AbstractStatusHandler {
    public TransitStatusHandler() {
        super(OrderState.TRANSIT);
    }

    @Override
    protected String getName() {
        return "";
    }
}

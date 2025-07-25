package com.bush.pharmacy_web_app.service.order.mapper.handler;

import com.bush.pharmacy_web_app.model.entity.order.state.OrderState;

public class CanceledStatusHandler extends AbstractStatusHandler {
    public CanceledStatusHandler() {
        super(OrderState.CANCELLED);
    }

    @Override
    protected String getName() {
        return "Отменен";
    }
}

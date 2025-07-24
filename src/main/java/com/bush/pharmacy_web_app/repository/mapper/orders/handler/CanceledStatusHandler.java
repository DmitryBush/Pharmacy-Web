package com.bush.pharmacy_web_app.repository.mapper.orders.handler;

import com.bush.pharmacy_web_app.repository.entity.order.state.OrderState;

public class CanceledStatusHandler extends AbstractStatusHandler {
    public CanceledStatusHandler() {
        super(OrderState.CANCELLED);
    }

    @Override
    protected String getName() {
        return "Отменен";
    }
}

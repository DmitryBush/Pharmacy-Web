package com.bush.pharmacy_web_app.service.order.mapper.handler;

import com.bush.pharmacy_web_app.model.entity.order.state.OrderState;

public class CompletedStatusHandler extends AbstractStatusHandler {
    public CompletedStatusHandler() {
        super(OrderState.COMPLETED);
    }

    @Override
    protected String getName() {
        return "Завершен";
    }
}

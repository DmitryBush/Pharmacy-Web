package com.bush.pharmacy_web_app.repository.mapper.orders.handler;

import com.bush.pharmacy_web_app.repository.entity.order.state.OrderState;

public class ReturnStatusHandler extends AbstractStatusHandler {
    public ReturnStatusHandler() {
        super(OrderState.RETURN_REQUESTED);
    }

    @Override
    protected String getName() {
        return "Возврат";
    }
}

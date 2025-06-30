package com.bush.pharmacy_web_app.repository.mapper.orders.handler;

import com.bush.pharmacy_web_app.repository.entity.order.state.OrderStatus;

public class ReturnStatusHandler extends AbstractStatusHandler {
    public ReturnStatusHandler() {
        super(OrderStatus.RETURN);
    }

    @Override
    protected String getName() {
        return "Возврат";
    }
}

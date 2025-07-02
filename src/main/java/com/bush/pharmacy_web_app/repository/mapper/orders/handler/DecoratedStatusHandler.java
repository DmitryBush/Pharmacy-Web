package com.bush.pharmacy_web_app.repository.mapper.orders.handler;

import com.bush.pharmacy_web_app.repository.entity.order.state.OrderState;

public class DecoratedStatusHandler extends AbstractStatusHandler {
    public DecoratedStatusHandler() {
        super(OrderState.DECOR);
    }

    @Override
    protected String getName() {
        return "Оформлен";
    }
}

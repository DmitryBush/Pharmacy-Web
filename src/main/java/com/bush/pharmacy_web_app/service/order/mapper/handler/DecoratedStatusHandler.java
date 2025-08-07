package com.bush.pharmacy_web_app.service.order.mapper.handler;

import com.bush.pharmacy_web_app.model.entity.order.state.OrderState;

public class DecoratedStatusHandler extends AbstractStatusHandler {
    public DecoratedStatusHandler() {
        super(OrderState.DECOR);
    }

    @Override
    protected String getName() {
        return "Оформлен";
    }
}

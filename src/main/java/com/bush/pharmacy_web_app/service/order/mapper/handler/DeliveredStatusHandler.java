package com.bush.pharmacy_web_app.service.order.mapper.handler;

import com.bush.pharmacy_web_app.model.entity.order.state.OrderState;

public class DeliveredStatusHandler extends AbstractStatusHandler {
    public DeliveredStatusHandler() {
        super(OrderState.DELIVERED);
    }

    @Override
    protected String getName() {
        return "Доставлено в пункт аптеки";
    }
}

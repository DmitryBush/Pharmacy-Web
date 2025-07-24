package com.bush.pharmacy_web_app.repository.mapper.orders.handler;

import com.bush.pharmacy_web_app.repository.entity.order.state.OrderState;

public class DeliveredStatusHandler extends AbstractStatusHandler {
    public DeliveredStatusHandler() {
        super(OrderState.DELIVERED);
    }

    @Override
    protected String getName() {
        return "Доставлено в пункт аптеки";
    }
}

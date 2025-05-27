package com.bush.pharmacy_web_app.repository.mapper.orders.handler;

import com.bush.pharmacy_web_app.repository.entity.order.OrderStatus;

public class NotDemandStatusHandler extends AbstractStatusHandler {
    public NotDemandStatusHandler() {
        super(OrderStatus.NOT_DEMAND);
    }

    @Override
    protected String getName() {
        return "Не востребован";
    }
}

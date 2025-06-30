package com.bush.pharmacy_web_app.repository.mapper.orders.handler;

import com.bush.pharmacy_web_app.repository.entity.order.state.OrderStatus;

public class AssemblyStatusHandler extends AbstractStatusHandler {
    public AssemblyStatusHandler() {
        super(OrderStatus.ASSEMBLY);
    }

    @Override
    protected String getName() {
        return "В сборке";
    }
}

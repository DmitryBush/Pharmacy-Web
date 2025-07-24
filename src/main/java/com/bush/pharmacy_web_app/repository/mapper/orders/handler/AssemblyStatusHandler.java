package com.bush.pharmacy_web_app.repository.mapper.orders.handler;

import com.bush.pharmacy_web_app.repository.entity.order.state.OrderState;

public class AssemblyStatusHandler extends AbstractStatusHandler {
    public AssemblyStatusHandler() {
        super(OrderState.ASSEMBLY);
    }

    @Override
    protected String getName() {
        return "В сборке";
    }
}

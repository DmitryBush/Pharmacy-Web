package com.bush.pharmacy_web_app.service.order.mapper.handler;

import com.bush.pharmacy_web_app.model.entity.order.state.OrderState;

public class AssemblyStatusHandler extends AbstractStatusHandler {
    public AssemblyStatusHandler() {
        super(OrderState.ASSEMBLY);
    }

    @Override
    protected String getName() {
        return "В сборке";
    }
}

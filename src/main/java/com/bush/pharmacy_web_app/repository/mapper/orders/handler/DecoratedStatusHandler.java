package com.bush.pharmacy_web_app.repository.mapper.orders.handler;

import com.bush.pharmacy_web_app.repository.entity.order.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DecoratedStatusHandler extends AbstractStatusHandler {
    @Autowired
    public DecoratedStatusHandler(AssemblyStatusHandler nextHandler) {
        super(OrderStatus.DECORATED);
        this.setNext(nextHandler);
    }

    @Override
    protected String getName() {
        return "Оформлен";
    }
}

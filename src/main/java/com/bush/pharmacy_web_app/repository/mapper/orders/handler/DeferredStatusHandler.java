package com.bush.pharmacy_web_app.repository.mapper.orders.handler;

import com.bush.pharmacy_web_app.repository.entity.order.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeferredStatusHandler extends AbstractStatusHandler {
    @Autowired
    public DeferredStatusHandler(DecoratedStatusHandler nextHandler) {
        super(OrderStatus.DEFERRED);
        this.setNext(nextHandler);
    }

    @Override
    protected String getName() {
        return "Отложен";
    }
}

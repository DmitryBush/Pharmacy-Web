package com.bush.pharmacy_web_app.repository.mapper.orders.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NameStatusHandler extends AbstractStatusHandler {
    @Autowired
    public NameStatusHandler(PaymentAwaitStatusHandler nextHandler) {
        super(null);
        this.setNext(nextHandler);
    }

    @Override
    protected String getName() {
        return "";
    }
}

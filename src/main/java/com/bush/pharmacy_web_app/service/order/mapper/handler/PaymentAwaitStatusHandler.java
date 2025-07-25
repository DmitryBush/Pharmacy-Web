package com.bush.pharmacy_web_app.service.order.mapper.handler;

import com.bush.pharmacy_web_app.model.entity.order.state.OrderState;


public class PaymentAwaitStatusHandler extends AbstractStatusHandler {
    public PaymentAwaitStatusHandler() {
        super(OrderState.PAYMENT_AWAIT);
    }

    @Override
    protected String getName() {
        return "Ожидает оплаты";
    }
}

package com.bush.pharmacy_web_app.repository.mapper.orders.handler;

import com.bush.pharmacy_web_app.repository.entity.order.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentAwaitStatusHandler extends AbstractStatusHandler {
    @Autowired
    public PaymentAwaitStatusHandler(CanceledStatusHandler nextHandler) {
        super(OrderStatus.PAYMENT_AWAIT);
        this.setNext(nextHandler);
    }

    @Override
    protected String getName() {
        return "Ожидает оплаты";
    }
}

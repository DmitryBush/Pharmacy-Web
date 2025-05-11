package com.bush.pharmacy_web_app.repository.mapper.orders.handler;

import com.bush.pharmacy_web_app.repository.entity.order.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompletedStatusHandler extends AbstractStatusHandler {
    @Autowired
    public CompletedStatusHandler(NotDemandStatusHandler nextHandler) {
        super(OrderStatus.COMPLETED);
        this.setNext(nextHandler);
    }

    @Override
    protected String getName() {
        return "Завершен";
    }
}

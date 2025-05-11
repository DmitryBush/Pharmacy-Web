package com.bush.pharmacy_web_app.repository.mapper.orders.handler;

import com.bush.pharmacy_web_app.repository.entity.order.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotDemandStatusHandler extends AbstractStatusHandler {
    @Autowired
    public NotDemandStatusHandler(ReturnStatusHandler nextHandler) {
        super(OrderStatus.NOT_DEMAND);
        this.setNext(nextHandler);
    }

    @Override
    protected String getName() {
        return "Не востребован";
    }
}

package com.bush.pharmacy_web_app.repository.mapper.orders.handler;

import com.bush.pharmacy_web_app.repository.entity.order.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeliveredStatusHandler extends AbstractStatusHandler {
    @Autowired
    public DeliveredStatusHandler(CompletedStatusHandler nextHandler) {
        super(OrderStatus.DELIVERED);
        this.setNext(nextHandler);
    }

    @Override
    protected String getName() {
        return "Доставлено в пункт аптеки";
    }
}

package com.bush.pharmacy_web_app.repository.mapper.orders.handler;

import com.bush.pharmacy_web_app.repository.entity.order.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssemblyStatusHandler extends AbstractStatusHandler{
    @Autowired
    public AssemblyStatusHandler(DeliveredStatusHandler handler) {
        super(OrderStatus.ASSEMBLY);
        this.setNext(handler);
    }

    @Override
    protected String getName() {
        return "В сборке";
    }
}

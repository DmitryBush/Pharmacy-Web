package com.bush.pharmacy_web_app.repository.mapper.orders.handler;

import com.bush.pharmacy_web_app.repository.entity.order.OrderStatus;
import jdk.jfr.Category;
import org.springframework.stereotype.Component;

@Component
public class ReturnStatusHandler extends AbstractStatusHandler {
    public ReturnStatusHandler() {
        super(OrderStatus.RETURN);
    }

    @Override
    protected String getName() {
        return "Возврат";
    }
}

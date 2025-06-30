package com.bush.pharmacy_web_app.repository.entity.order.state;

public interface OrderState {
    OrderStatus completeOrder();
    OrderStatus cancelOrder();
    OrderStatus completePhase();
}

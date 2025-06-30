package com.bush.pharmacy_web_app.repository.entity.order.state;

public class DeliveredOrderState implements OrderState {
    @Override
    public OrderStatus completeOrder() {
        return OrderStatus.COMPLETED;
    }

    @Override
    public OrderStatus cancelOrder() {
        return OrderStatus.CANCELED;
    }

    @Override
    public OrderStatus completePhase() {
        return OrderStatus.NOT_DEMAND;
    }
}

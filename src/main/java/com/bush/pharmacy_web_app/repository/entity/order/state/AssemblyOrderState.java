package com.bush.pharmacy_web_app.repository.entity.order.state;

import com.bush.pharmacy_web_app.repository.entity.order.state.exception.InvalidStateTransitionException;

public class AssemblyOrderState implements OrderState{
    @Override
    public OrderStatus completeOrder() {
        throw new InvalidStateTransitionException("Impossible to transit from this state");
    }

    @Override
    public OrderStatus cancelOrder() {
        return OrderStatus.CANCELED;
    }

    @Override
    public OrderStatus completePhase() {
        return OrderStatus.TRANSIT;
    }
}

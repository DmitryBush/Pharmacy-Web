package com.bush.pharmacy_web_app.repository.entity.order.state;

import com.bush.pharmacy_web_app.repository.entity.order.state.exception.InvalidStateTransitionException;

public class NotDemandOrderState implements OrderState {
    @Override
    public OrderStatus completeOrder() {
        throw new InvalidStateTransitionException("Impossible to transit from this state");
    }

    @Override
    public OrderStatus cancelOrder() {
        throw new InvalidStateTransitionException("Impossible to transit from this state");
    }

    @Override
    public OrderStatus completePhase() {
        throw new InvalidStateTransitionException("Impossible to transit from this state");
    }
}

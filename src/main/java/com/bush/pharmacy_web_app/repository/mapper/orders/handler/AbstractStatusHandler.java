package com.bush.pharmacy_web_app.repository.mapper.orders.handler;

import com.bush.pharmacy_web_app.repository.entity.order.OrderStatus;

public abstract class AbstractStatusHandler implements StatusHandler {
    private final OrderStatus status;
    private StatusHandler nextHandler;

    public AbstractStatusHandler(OrderStatus status) {
        this.status = status;
    }

    @Override
    public void setNext(StatusHandler handler) {
        this.nextHandler = handler;
    }

    @Override
    public String handle(OrderStatus status) {
        if (status.equals(this.status))
            return getName();

        if (nextHandler != null)
            nextHandler.handle(status);
        throw new IllegalArgumentException("There is no handler for this status status");
    }

    protected abstract String getName();
}

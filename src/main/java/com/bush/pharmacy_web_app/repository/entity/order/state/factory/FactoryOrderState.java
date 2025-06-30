package com.bush.pharmacy_web_app.repository.entity.order.state.factory;

import com.bush.pharmacy_web_app.repository.entity.order.state.*;

public class FactoryOrderState {
    public static OrderState getState(OrderStatus orderStatus) {
        switch (orderStatus) {
            case PAYMENT_AWAIT -> {
                return new PaymentAwaitsOrderState();
            }
            case DECORATED -> {
                return new DecoratedOrderState();
            }
            case ASSEMBLY -> {
                return new AssemblyOrderState();
            }
            case TRANSIT -> {
                return new TransitOrderState();
            }
            case DELIVERED -> {
                return new DeliveredOrderState();
            }
            case CANCELED -> {
                return new CanceledOrderState();
            }
            case COMPLETED -> {
                return new CompletedOrderState();
            }
            case RETURN -> {
                return new ReturnOrderState();
            }
            case NOT_DEMAND -> {
                return new NotDemandOrderState();
            }
            case DEFERRED -> {
                return new DeferredOrderState();
            }
            default -> throw new IllegalArgumentException();
        }
    }
}

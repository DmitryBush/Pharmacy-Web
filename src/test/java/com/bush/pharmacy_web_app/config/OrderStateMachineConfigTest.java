package com.bush.pharmacy_web_app.config;

import com.bush.pharmacy_web_app.repository.entity.order.state.OrderEvent;
import com.bush.pharmacy_web_app.repository.entity.order.state.OrderState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.config.StateMachineFactory;
import reactor.core.publisher.Mono;

import java.util.UUID;

@SpringBootTest
public class OrderStateMachineConfigTest {
    @Autowired
    StateMachineFactory<OrderState, OrderEvent> factory;

    @Test
    void testNormalLifecycleStateMachine() {
        var sm = factory.getStateMachine(UUID.randomUUID());

        sm.startReactively()
                .thenMany(sm.sendEvent(Mono.just(MessageBuilder.withPayload(OrderEvent.PAYMENT_RECEIVED).build())))
                .thenMany(sm.sendEvent(Mono.just(MessageBuilder.withPayload(OrderEvent.DECORATED_ORDER).build())))
                .thenMany(sm.sendEvent(Mono.just(MessageBuilder.withPayload(OrderEvent.SHIPPED_ORDER).build())))
                .thenMany(sm.sendEvent(Mono.just(MessageBuilder.withPayload(OrderEvent.DELIVERED_ORDER).build())))
                .thenMany(sm.sendEvent(Mono.just(MessageBuilder.withPayload(OrderEvent.OPERATOR_COMPLETES_ORDER).build())))
                .subscribe(result ->
                                Assertions.assertEquals(OrderState.COMPLETED, sm.getState().getId()),
                        System.out::println
                );
    }

    @Test
    void testOrderCancelledByUser() {
        var sm = factory.getStateMachine(UUID.randomUUID());

        sm.startReactively()
                .thenMany(sm.sendEvent(Mono.just(MessageBuilder.withPayload(OrderEvent.PAYMENT_RECEIVED).build())))
                .thenMany(sm.sendEvent(Mono.just(MessageBuilder.withPayload(OrderEvent.DECORATED_ORDER).build())))
                .thenMany(sm.sendEvent(Mono.just(MessageBuilder.withPayload(OrderEvent.ORDER_CANCELLED_BY_USER).build())))
                .subscribe(result ->
                                Assertions.assertEquals(OrderState.CANCELLED, sm.getState().getId()),
                        System.out::println
                );
    }
}

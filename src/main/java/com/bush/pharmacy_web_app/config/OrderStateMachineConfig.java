package com.bush.pharmacy_web_app.config;

import com.bush.pharmacy_web_app.repository.entity.order.state.OrderEvent;
import com.bush.pharmacy_web_app.repository.entity.order.state.OrderState;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory
public class OrderStateMachineConfig extends StateMachineConfigurerAdapter<OrderState, OrderEvent> {
    @Override
    public void configure(StateMachineStateConfigurer<OrderState, OrderEvent> states) throws Exception {
        states.withStates()
                .initial(OrderState.PAYMENT_AWAIT)
                .states(EnumSet.allOf(OrderState.class))
                .end(OrderState.CANCELLED)
                .end(OrderState.COMPLETED)
                .end(OrderState.NOT_DEMAND)
                .end(OrderState.RETURN_CLOSED)
                .end(OrderState.RETURN_REJECTED);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<OrderState, OrderEvent> transitions) throws Exception {
        transitions
                .withExternal()
                    .source(OrderState.PAYMENT_AWAIT)
                    .target(OrderState.CANCELLED)
                    .event(OrderEvent.PAYMENT_TIME_EXPIRED)
                .and()
                .withExternal()
                    .source(OrderState.PAYMENT_AWAIT)
                    .target(OrderState.DECOR)
                    .event(OrderEvent.PAYMENT_RECEIVED)
                .and()
                .withExternal()
                    .source(OrderState.PAYMENT_AWAIT)
                    .target(OrderState.DECOR)
                    .event(OrderEvent.POST_PAYMENT_METHOD_SELECTED)
                .and()

                .withExternal()
                    .source(OrderState.DECOR)
                    .target(OrderState.ASSEMBLY)
                    .event(OrderEvent.DECORATED_ORDER)
                .and()
                .withExternal()
                    .source(OrderState.DECOR)
                    .target(OrderState.DEFERRED)
                    .event(OrderEvent.SELLER_ISSUE)
                .and()
                .withExternal()
                    .source(OrderState.DECOR)
                    .target(OrderState.CANCELLED)
                    .event(OrderEvent.ORDER_CANCELLED_BY_USER)
                .and()

                .withExternal()
                    .source(OrderState.ASSEMBLY)
                    .target(OrderState.TRANSIT)
                    .event(OrderEvent.SHIPPED_ORDER)
                .and()
                .withExternal()
                    .source(OrderState.ASSEMBLY)
                    .target(OrderState.DEFERRED)
                    .event(OrderEvent.WAREHOUSE_ISSUE)
                .and()
                .withExternal()
                    .source(OrderState.ASSEMBLY)
                    .target(OrderState.CANCELLED)
                    .event(OrderEvent.ORDER_CANCELLED_BY_USER)
                .and()

                .withExternal()
                    .source(OrderState.TRANSIT)
                    .target(OrderState.DELIVERED)
                    .event(OrderEvent.DELIVERED_ORDER)
                .and()
                .withExternal()
                    .source(OrderState.TRANSIT)
                    .target(OrderState.DEFERRED)
                    .event(OrderEvent.LOGISTIC_ISSUE)
                .and()
                .withExternal()
                    .source(OrderState.TRANSIT)
                    .target(OrderState.CANCELLED)
                    .event(OrderEvent.ORDER_CANCELLED_BY_USER)
                .and()

                .withExternal()
                    .source(OrderState.DEFERRED)
                    .target(OrderState.ASSEMBLY)
                    .event(OrderEvent.DECORATED_ORDER)
                .and()
                .withExternal()
                    .source(OrderState.DEFERRED)
                    .target(OrderState.TRANSIT)
                    .event(OrderEvent.SHIPPED_ORDER)
                .and()
                .withExternal()
                    .source(OrderState.DEFERRED)
                    .target(OrderState.DELIVERED)
                    .event(OrderEvent.DELIVERED_ORDER)
                .and()

                .withExternal()
                    .source(OrderState.DELIVERED)
                    .target(OrderState.COMPLETED)
                    .event(OrderEvent.OPERATOR_COMPLETES_ORDER)
                .and()
                .withExternal()
                    .source(OrderState.DELIVERED)
                    .target(OrderState.NOT_DEMAND)
                    .event(OrderEvent.OPERATOR_RETURNS_ORDER)
                .and()

                .withExternal()
                    .source(OrderState.COMPLETED)
                    .target(OrderState.RETURN_REQUESTED)
                    .event(OrderEvent.RETURN_REQUESTED_BY_USER)
                .and()

                .withExternal()
                    .source(OrderState.RETURN_REQUESTED)
                    .target(OrderState.AWAITING_CUSTOMER_SHIPMENT)
                    .event(OrderEvent.RETURN_APPROVED)
                .and()
                .withExternal()
                    .source(OrderState.RETURN_REQUESTED)
                    .target(OrderState.RETURN_REJECTED)
                    .event(OrderEvent.RETURN_DISAPPROVED)
                .and()

                .withExternal()
                    .source(OrderState.AWAITING_CUSTOMER_SHIPMENT)
                    .target(OrderState.RETURN_SHIPPED_BY_CUSTOMER)
                    .event(OrderEvent.ORDER_RETURNED_BY_USER)
                .and()
                .withExternal()
                    .source(OrderState.AWAITING_CUSTOMER_SHIPMENT)
                    .target(OrderState.RETURN_REJECTED)
                    .event(OrderEvent.RETURN_TIME_EXPIRED)
                .and()

                .withExternal()
                    .source(OrderState.RETURN_SHIPPED_BY_CUSTOMER)
                    .target(OrderState.RETURN_TRANSIT)
                    .event(OrderEvent.SHIPPED_RETURN)
                .and()
                .withExternal()
                    .source(OrderState.RETURN_SHIPPED_BY_CUSTOMER)
                    .target(OrderState.RETURN_DEFERRED)
                    .event(OrderEvent.WAREHOUSE_ISSUE)
                .and()

                .withExternal()
                    .source(OrderState.RETURN_TRANSIT)
                    .target(OrderState.RETURN_CLOSED)
                    .event(OrderEvent.DELIVERED_RETURN)
                .and()
                .withExternal()
                    .source(OrderState.RETURN_TRANSIT)
                    .target(OrderState.RETURN_DEFERRED)
                    .event(OrderEvent.LOGISTIC_ISSUE)
                .and()

                .withExternal()
                    .source(OrderState.RETURN_DEFERRED)
                    .target(OrderState.RETURN_TRANSIT)
                    .event(OrderEvent.SHIPPED_RETURN)
                .and()
                .withExternal()
                    .source(OrderState.RETURN_DEFERRED)
                    .target(OrderState.RETURN_CLOSED)
                    .event(OrderEvent.DELIVERED_RETURN);
    }
}

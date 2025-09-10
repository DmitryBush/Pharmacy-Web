package com.bush.pharmacy_web_app.service.order;

import com.bush.pharmacy_web_app.repository.order.OrderRepository;
import com.bush.pharmacy_web_app.model.entity.order.state.OrderEvent;
import com.bush.pharmacy_web_app.model.entity.order.state.OrderState;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderStateInterceptorListener extends StateMachineInterceptorAdapter<OrderState, OrderEvent> {
    private final OrderRepository orderRepository;

    @Override
    public void preStateChange(State<OrderState, OrderEvent> state,
                               Message<OrderEvent> message,
                               Transition<OrderState, OrderEvent> transition,
                               StateMachine<OrderState, OrderEvent> stateMachine,
                               StateMachine<OrderState, OrderEvent> rootStateMachine) {
        Optional.ofNullable(message)
                .flatMap(msg -> Optional.ofNullable((Long) msg.getHeaders()
                                .getOrDefault(OrderService.ORDER_HEADER, -1L)))
                .ifPresent(orderId -> {
                    var order = orderRepository.findById(orderId).orElseThrow();
                    order.setStatus(state.getId());
                    orderRepository.save(order);
        });
    }
}

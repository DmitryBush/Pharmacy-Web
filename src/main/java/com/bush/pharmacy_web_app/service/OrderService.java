package com.bush.pharmacy_web_app.service;

import com.bush.pharmacy_web_app.repository.OrderRepository;
import com.bush.pharmacy_web_app.repository.dto.orders.AdminOrderDto;
import com.bush.pharmacy_web_app.repository.entity.order.state.OrderEvent;
import com.bush.pharmacy_web_app.repository.entity.order.state.OrderState;
import com.bush.pharmacy_web_app.repository.mapper.orders.AdminOrderReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    private final AdminOrderReadMapper adminOrderReadMapper;

    private final StateMachineFactory<OrderState, OrderEvent> stateMachineFactory;
    private final OrderStateInterceptorListener stateInterceptorListener;
    public static final String ORDER_HEADER = "orderId";

    public Page<AdminOrderDto> findAllOrdersByBranch(Long id, Pageable pageable) {
        return orderRepository.findByBranchId(id, pageable)
                .map(adminOrderReadMapper::map);
    }

    public Optional<AdminOrderDto> findOrderById(Long id) {
        return orderRepository.findById(id)
                .map(adminOrderReadMapper::map);
    }

    /**
     * Handles the ability to transition between order states.
     * TODO: Switch to reactive implementation of Spring State Machine
     * @param orderId The order whose status needs to be changed
     * @param event The event that causes a change in state
     * @return If true, then the state was changed.
     */
    @Transactional
    public Boolean processEvent(Long orderId, OrderEvent event) {
        return build(orderId)
                .sendEvent(MessageBuilder.createMessage(event, new MessageHeaders(Map.of(ORDER_HEADER, orderId))));
    }

    private StateMachine<OrderState, OrderEvent> build(Long orderId) {
        var order = orderRepository.findById(orderId)
                .orElseThrow();
        var sm = stateMachineFactory.getStateMachine(Long.toString(orderId));

        sm.stop();

        sm.getStateMachineAccessor()
                .doWithAllRegions(sma -> {
                    sma.addStateMachineInterceptor(stateInterceptorListener);
                    sma.resetStateMachine(new DefaultStateMachineContext<>(order.getStatus(), null,
                            null, null));
                });
        sm.start();
        return sm;
    }
}

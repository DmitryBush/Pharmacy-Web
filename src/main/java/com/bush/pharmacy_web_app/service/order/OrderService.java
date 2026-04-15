package com.bush.pharmacy_web_app.service.order;

import com.bush.pharmacy_web_app.model.dto.orders.OrderCreateDto;
import com.bush.pharmacy_web_app.model.dto.orders.OrderReadDto;
import com.bush.pharmacy_web_app.model.entity.branch.PharmacyBranch;
import com.bush.pharmacy_web_app.model.entity.order.OrderItem;
import com.bush.pharmacy_web_app.model.entity.user.User;
import com.bush.pharmacy_web_app.repository.order.OrderRepository;
import com.bush.pharmacy_web_app.model.dto.orders.AdminOrderDto;
import com.bush.pharmacy_web_app.model.entity.order.Order;
import com.bush.pharmacy_web_app.model.entity.order.state.OrderEvent;
import com.bush.pharmacy_web_app.model.entity.order.state.OrderState;
import com.bush.pharmacy_web_app.service.branch.PharmacyBranchService;
import com.bush.pharmacy_web_app.service.order.mapper.AdminOrderReadMapper;
import com.bush.pharmacy_web_app.service.order.mapper.OrderCreateMapper;
import com.bush.pharmacy_web_app.service.order.mapper.OrderItemCreateMapper;
import com.bush.pharmacy_web_app.service.order.mapper.OrderReadMapper;
import com.bush.pharmacy_web_app.service.product.ProductService;
import com.bush.pharmacy_web_app.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    private final UserService userService;
    private final PharmacyBranchService branchService;
    private final ProductService productService;

    private final OrderCreateMapper orderCreateMapper;
    private final OrderItemCreateMapper orderItemCreateMapper;
    private final AdminOrderReadMapper adminOrderReadMapper;
    private final OrderReadMapper orderReadMapper;

    private final StateMachineFactory<OrderState, OrderEvent> stateMachineFactory;
    private final OrderStateInterceptorListener stateInterceptorListener;
    public static final String ORDER_HEADER = "orderId";

    public Page<AdminOrderDto> findAllOrdersByBranch(Long branchId, Pageable pageable) {
        return orderRepository.findByBranchId(branchId, pageable)
                .map(adminOrderReadMapper::map);
    }

    public Optional<AdminOrderDto> findAdminOrderInfoById(UUID id) {
        return orderRepository.findById(id)
                .map(adminOrderReadMapper::map);
    }

    public Optional<OrderState> findOrderStateById(UUID id) {
        return orderRepository.findById(id)
                .map(Order::getStatus);
    }

    /**
     * Handles the ability to transition between order states.
     * TODO: Switch to reactive implementation of Spring State Machine
     * @param order The order whose status needs to be changed
     * @param event The event that causes a change in state
     * @return If true, then the state was changed.
     */
    @Transactional
    public Boolean processEvent(Order order, OrderEvent event) {
        return build(order)
                .sendEvent(MessageBuilder.createMessage(event, new MessageHeaders(Map.of(ORDER_HEADER, order.getId()))));
    }

    private StateMachine<OrderState, OrderEvent> build(Order order) {
        var sm = stateMachineFactory.getStateMachine(order.getId());

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

    public Page<OrderReadDto> findAllUserOrders(String userId, Pageable pageable) {
        return orderRepository.findAllUserOrders(userId, pageable)
                .map(orderReadMapper::map);
    }

    @PostAuthorize("returnObject.userId.equals(authentication.principal.username)")
    public OrderReadDto findUserOrderById(UUID uuid) {
        return orderRepository.findById(uuid)
                .map(orderReadMapper::map)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public OrderReadDto createOrder(OrderCreateDto createDto, String userId) {
        User user = userService.getUserReferenceById(userId);
        PharmacyBranch branch = branchService.getReferenceById(branchService.findBranchInfoById(createDto.branchId()).id());
        List<OrderItem> orderItems = createDto.orderItems().stream()
                .map(dtoItem -> orderItemCreateMapper.mapToOrderItem(dtoItem,
                        productService.getReferenceById(dtoItem.productId())))
                .toList();
        return Optional.of(createDto)
                .map(dto -> orderCreateMapper.mapToOrder(dto, user, branch, orderItems))
                .map(orderRepository::save)
                .map(orderReadMapper::map)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    @PreAuthorize("T(com.bush.pharmacy_web_app.model.entity.order.state.OrderEventPermissionMapping)" +
            ".canOrderEventProcessed(authentication.principal.authorities,#event)")
    @Transactional
    public boolean changeOrderStatusByEvent(UUID orderId, String userId, OrderEvent event) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (order.getUser().getMobilePhone().equals(userId)) {
            return processEvent(order, event);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @PreAuthorize("T(com.bush.pharmacy_web_app.model.entity.order.state.OrderEventPermissionMapping)" +
            ".canOrderEventProcessed(authentication.principal.authorities,#event)")
    @Transactional
    public boolean changeOrderStatusByEvent(UUID orderId, OrderEvent event) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return processEvent(order, event);
    }
}

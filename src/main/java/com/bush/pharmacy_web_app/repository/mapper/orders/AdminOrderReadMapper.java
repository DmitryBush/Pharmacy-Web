package com.bush.pharmacy_web_app.repository.mapper.orders;

import com.bush.pharmacy_web_app.repository.dto.orders.AdminOrderDto;
import com.bush.pharmacy_web_app.repository.entity.order.Order;
import com.bush.pharmacy_web_app.repository.mapper.DtoMapper;
import com.bush.pharmacy_web_app.repository.mapper.user.AdminUserReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AdminOrderReadMapper implements DtoMapper<Order, AdminOrderDto> {
    private final PharmacyBranchReadMapper branchReadMapper;
    private final OrderItemReadMapper orderReadMapper;
    private final OrderStatusReadMapper orderStatusReadMapper;
    private final AdminUserReadMapper adminUserReadMapper;
    @Override
    public AdminOrderDto map(Order obj) {
        var status = Optional.ofNullable(obj.getStatus())
                .map(orderStatusReadMapper::map)
                .orElseThrow();
        var branch = Optional.ofNullable(obj.getBranch())
                .map(branchReadMapper::map)
                .orElse(null);
        var cart = Optional.ofNullable(obj.getOrderItemList())
                .map(itemList -> itemList.stream().map(orderReadMapper::map).toList())
                .orElseThrow();
        var customer = Optional.ofNullable(obj.getUser())
                .map(adminUserReadMapper::map)
                .orElseThrow();
        BigDecimal result = cart.stream()
                .map(lamb -> lamb.medicine().price().multiply(BigDecimal.valueOf(lamb.amount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new AdminOrderDto(obj.getId(), status, obj.getDate(),customer, branch, cart, result);
    }
}

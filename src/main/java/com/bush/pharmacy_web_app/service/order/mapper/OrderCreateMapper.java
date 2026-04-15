package com.bush.pharmacy_web_app.service.order.mapper;

import com.bush.pharmacy_web_app.model.dto.orders.OrderCreateDto;
import com.bush.pharmacy_web_app.model.entity.branch.PharmacyBranch;
import com.bush.pharmacy_web_app.model.entity.order.Order;
import com.bush.pharmacy_web_app.model.entity.order.OrderItem;
import com.bush.pharmacy_web_app.model.entity.order.state.OrderState;
import com.bush.pharmacy_web_app.model.entity.user.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.time.Instant;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {OrderState.class, Instant.class},
        uses = OrderItemCreateMapper.class)
public interface OrderCreateMapper {
    @Mapping(target = "status", expression = "java(OrderState.DECOR)")
    @Mapping(target = "date", expression = "java(Instant.now())")
    @Mapping(target = "id", ignore = true)
    Order mapToOrder(OrderCreateDto createDto, User user, PharmacyBranch branch, List<OrderItem> orderItemList);

    @AfterMapping
    default void mapOrderToItems(@MappingTarget Order order) {
        order.getOrderItemList().forEach(orderItem -> orderItem.setOrder(order));
    }
}

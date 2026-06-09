package com.bush.pharmacy_web_app.service.order.mapper;

import com.bush.pharmacy_web_app.model.dto.orders.OrderItemReadDto;
import com.bush.pharmacy_web_app.model.dto.orders.OrderReadDto;
import com.bush.pharmacy_web_app.model.dto.orders.OrderStatusDto;
import com.bush.pharmacy_web_app.model.entity.order.Order;
import com.bush.pharmacy_web_app.model.entity.order.OrderItem;
import com.bush.pharmacy_web_app.model.entity.order.state.OrderState;
import com.bush.pharmacy_web_app.service.branch.mapper.PharmacyBranchReadMapper;
import com.bush.pharmacy_web_app.service.cart.mapper.CartItemReadMapper;
import com.bush.pharmacy_web_app.service.product.mapper.ProductReadMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.math.BigDecimal;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {PharmacyBranchReadMapper.class, CartItemReadMapper.class, ProductReadMapper.class})
public interface OrderReadMapper {
    @Mapping(target = "userId", source = "user.mobilePhone")
    @Mapping(target = "statusOrder", source = "status")
    @Mapping(target = "cartItems", source = "orderItemList")
    @Mapping(target = "result", source = "order", qualifiedByName = "countResultOrderPrice")
    OrderReadDto mapToOrderReadDto(Order order);

    @Mapping(target = "id", expression = "java(status.ordinal())")
    @Mapping(target = "name", expression = "java(status.name())")
    OrderStatusDto mapToOrderStatusDto(OrderState status);

    @Mapping(target = "medicine", source = "product")
    OrderItemReadDto mapToOrderItemReadDto(OrderItem item);

    @Named("countResultOrderPrice")
    default BigDecimal countResultOrderPrice(Order order) {
        BigDecimal result = BigDecimal.ZERO;
        for (OrderItem item : order.getOrderItemList()) {
            result = result.add(item.getPrice().multiply(BigDecimal.valueOf(item.getAmount())));
        }
        return result;
    }
}

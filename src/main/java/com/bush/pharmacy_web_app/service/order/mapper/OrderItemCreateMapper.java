package com.bush.pharmacy_web_app.service.order.mapper;

import com.bush.pharmacy_web_app.model.dto.orders.OrderItemCreateDto;
import com.bush.pharmacy_web_app.model.entity.order.Order;
import com.bush.pharmacy_web_app.model.entity.order.OrderItem;
import com.bush.pharmacy_web_app.model.entity.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderItemCreateMapper {
    @Mapping(target = "amount", source = "createDto.quantity")
    @Mapping(target = "price", source = "createDto.price")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    OrderItem mapToOrderItem(OrderItemCreateDto createDto, Product product);
}

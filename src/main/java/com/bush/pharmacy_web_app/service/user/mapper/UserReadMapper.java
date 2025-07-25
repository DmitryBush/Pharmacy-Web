package com.bush.pharmacy_web_app.service.user.mapper;

import com.bush.pharmacy_web_app.model.dto.user.CustomerReadDto;
import com.bush.pharmacy_web_app.model.entity.user.User;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
import com.bush.pharmacy_web_app.service.order.mapper.OrderReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserReadMapper implements DtoMapper<User, CustomerReadDto> {
    private final OrderReadMapper orderReadMapper;

    @Override
    public CustomerReadDto map(User obj) {
        var orders = Optional.ofNullable(obj.getOrders())
                .map(list -> list.stream()
                        .map(orderReadMapper::map)
                        .toList())
                .orElse(Collections.emptyList());
        return new CustomerReadDto(obj.getMobilePhone(), obj.getName(), obj.getSurname(), obj.getLastName(), orders);
    }
}

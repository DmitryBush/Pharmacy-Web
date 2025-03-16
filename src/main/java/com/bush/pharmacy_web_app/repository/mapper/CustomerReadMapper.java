package com.bush.pharmacy_web_app.repository.mapper;

import com.bush.pharmacy_web_app.repository.dto.CustomerReadDto;
import com.bush.pharmacy_web_app.repository.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomerReadMapper implements DtoMapper<Customer, CustomerReadDto> {
    private final OrderReadMapper orderReadMapper;

    @Override
    public CustomerReadDto map(Customer obj) {
        var orders = Optional.ofNullable(obj.getOrders())
                .map(list -> list.stream()
                        .map(orderReadMapper::map)
                        .toList())
                .orElse(Collections.emptyList());
        return new CustomerReadDto(obj.getMobilePhone(), obj.getName(), obj.getSurname(), obj.getLastName(), orders);
    }
}

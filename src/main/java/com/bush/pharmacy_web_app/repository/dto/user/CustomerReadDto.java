package com.bush.pharmacy_web_app.repository.dto.user;

import com.bush.pharmacy_web_app.repository.dto.orders.OrderReadDto;

import java.util.List;

public record CustomerReadDto(String mobilePhone,
                              String name,
                              String surname,
                              String lastName,
                              List<OrderReadDto> orders) { }

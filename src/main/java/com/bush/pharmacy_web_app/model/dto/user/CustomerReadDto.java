package com.bush.pharmacy_web_app.model.dto.user;

import com.bush.pharmacy_web_app.model.dto.orders.OrderReadDto;

import java.util.List;

public record CustomerReadDto(String mobilePhone,
                              String name,
                              String surname,
                              String lastName,
                              List<OrderReadDto> orders) { }

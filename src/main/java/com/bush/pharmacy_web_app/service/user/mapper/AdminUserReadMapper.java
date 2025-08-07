package com.bush.pharmacy_web_app.service.user.mapper;

import com.bush.pharmacy_web_app.model.dto.user.AdminCustomerReadDto;
import com.bush.pharmacy_web_app.model.entity.user.User;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
import org.springframework.stereotype.Component;

@Component
public class AdminUserReadMapper implements DtoMapper<User, AdminCustomerReadDto> {
    @Override
    public AdminCustomerReadDto map(User obj) {
        return new AdminCustomerReadDto(obj.getMobilePhone(), obj.getName(), obj.getSurname(), obj.getLastName());
    }
}

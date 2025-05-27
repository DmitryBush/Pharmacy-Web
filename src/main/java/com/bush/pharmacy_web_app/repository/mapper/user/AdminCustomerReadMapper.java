package com.bush.pharmacy_web_app.repository.mapper.user;

import com.bush.pharmacy_web_app.repository.dto.user.AdminCustomerReadDto;
import com.bush.pharmacy_web_app.repository.entity.User;
import com.bush.pharmacy_web_app.repository.mapper.DtoMapper;
import org.springframework.stereotype.Component;

@Component
public class AdminCustomerReadMapper implements DtoMapper<User, AdminCustomerReadDto> {
    @Override
    public AdminCustomerReadDto map(User obj) {
        return new AdminCustomerReadDto(obj.getMobilePhone(), obj.getName(), obj.getSurname(), obj.getLastName());
    }
}

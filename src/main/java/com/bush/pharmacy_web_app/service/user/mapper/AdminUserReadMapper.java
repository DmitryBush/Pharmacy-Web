package com.bush.pharmacy_web_app.service.user.mapper;

import com.bush.pharmacy_web_app.model.dto.user.AdminUserReadDto;
import com.bush.pharmacy_web_app.model.entity.user.User;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
import org.springframework.stereotype.Component;

@Component
public class AdminUserReadMapper implements DtoMapper<User, AdminUserReadDto> {
    @Override
    public AdminUserReadDto map(User obj) {
        return new AdminUserReadDto(obj.getMobilePhone(), obj.getName(), obj.getSurname(),
                obj.getLastName(), obj.getRole().getType().name());
    }
}

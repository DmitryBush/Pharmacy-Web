package com.bush.pharmacy_web_app.service.user.mapper;

import com.bush.pharmacy_web_app.model.dto.user.UserInfoDto;
import com.bush.pharmacy_web_app.model.entity.user.User;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
import org.springframework.stereotype.Component;

@Component
public class UserInfoMapper implements DtoMapper<User, UserInfoDto> {
    @Override
    public UserInfoDto map(User obj) {
        return new UserInfoDto(obj.getMobilePhone(), obj.getName(), obj.getSurname(), obj.getLastName());
    }
}

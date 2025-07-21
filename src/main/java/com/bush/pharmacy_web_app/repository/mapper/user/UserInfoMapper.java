package com.bush.pharmacy_web_app.repository.mapper.user;

import com.bush.pharmacy_web_app.repository.dto.user.UserInfoDto;
import com.bush.pharmacy_web_app.repository.entity.user.User;
import com.bush.pharmacy_web_app.repository.mapper.DtoMapper;
import org.springframework.stereotype.Component;

@Component
public class UserInfoMapper implements DtoMapper<User, UserInfoDto> {
    @Override
    public UserInfoDto map(User obj) {
        return new UserInfoDto(obj.getMobilePhone(), obj.getName(), obj.getSurname(), obj.getLastName());
    }
}

package com.bush.pharmacy_web_app.service.user.mapper;

import com.bush.pharmacy_web_app.model.dto.user.CustomerCreateDto;
import com.bush.pharmacy_web_app.model.entity.user.User;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class UserCreateMapper implements DtoMapper<CustomerCreateDto, User> {
    private final PasswordEncoder passwordEncoder;

    @Override
    public User map(CustomerCreateDto obj) {
        return copyObj(obj, new User());
    }

    @Override
    public User map(CustomerCreateDto fromObj, User toObj) {
        return copyObj(fromObj, toObj);
    }

    private User copyObj(CustomerCreateDto fromObj, User toObj) {
        toObj.setName(fromObj.name());
        toObj.setSurname(fromObj.surname());
        toObj.setLastName(fromObj.lastName());
        toObj.setMobilePhone(fromObj.mobilePhone());
        toObj.setPassword(passwordEncoder.encode(fromObj.password()));
        toObj.setOrders(Collections.emptyList());
        return toObj;
    }
}

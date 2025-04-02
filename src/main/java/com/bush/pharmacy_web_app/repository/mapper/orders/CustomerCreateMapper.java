package com.bush.pharmacy_web_app.repository.mapper.orders;

import com.bush.pharmacy_web_app.repository.dto.orders.CustomerCreateDto;
import com.bush.pharmacy_web_app.repository.entity.User;
import com.bush.pharmacy_web_app.repository.mapper.DtoMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CustomerCreateMapper implements DtoMapper<CustomerCreateDto, User> {

    @Override
    public User map(CustomerCreateDto obj) {
        return copyObj(obj, new User());
    }

    @Override
    public User map(CustomerCreateDto fromObj, User toObj) {
        return copyObj(fromObj, toObj);
    }

    private static User copyObj(CustomerCreateDto fromObj, User toObj) {
        toObj.setName(fromObj.name());
        toObj.setSurname(fromObj.surname());
        toObj.setLastName(fromObj.lastName());
        toObj.setMobilePhone(fromObj.mobilePhone());
        toObj.setPassword(fromObj.password());
        toObj.setOrders(Collections.emptyList());
        return toObj;
    }
}

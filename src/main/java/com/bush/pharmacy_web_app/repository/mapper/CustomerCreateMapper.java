package com.bush.pharmacy_web_app.repository.mapper;

import com.bush.pharmacy_web_app.repository.dto.CustomerCreateDto;
import com.bush.pharmacy_web_app.repository.entity.Customer;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CustomerCreateMapper implements DtoMapper<CustomerCreateDto, Customer> {

    @Override
    public Customer map(CustomerCreateDto obj) {
        return copyObj(obj, new Customer());
    }

    @Override
    public Customer map(CustomerCreateDto fromObj, Customer toObj) {
        copyObj(fromObj, toObj);
        return copyObj(fromObj, toObj);
    }

    private static Customer copyObj(CustomerCreateDto fromObj, Customer toObj) {
        toObj.setName(fromObj.name());
        toObj.setSurname(fromObj.surname());
        toObj.setLastName(fromObj.lastName());
        toObj.setMobilePhone(fromObj.mobilePhone());
        toObj.setOrders(Collections.emptyList());
        return toObj;
    }
}

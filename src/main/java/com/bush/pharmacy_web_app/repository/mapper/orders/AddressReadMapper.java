package com.bush.pharmacy_web_app.repository.mapper.orders;

import com.bush.pharmacy_web_app.repository.dto.orders.AddressReadDto;
import com.bush.pharmacy_web_app.repository.entity.Address;
import com.bush.pharmacy_web_app.repository.mapper.DtoMapper;
import org.springframework.stereotype.Component;

@Component
public class AddressReadMapper implements DtoMapper<Address, AddressReadDto> {
    @Override
    public AddressReadDto map(Address obj) {
        return new AddressReadDto(obj.getId(), obj.getSubject(), obj.getDistrict(), obj.getSettlement(),
                obj.getStreet(), obj.getHouse(), obj.getApartment(), obj.getPostalCode());
    }
}

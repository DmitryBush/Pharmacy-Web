package com.bush.pharmacy_web_app.repository.mapper;

import com.bush.pharmacy_web_app.repository.dto.catalog.AddressCreateDto;
import com.bush.pharmacy_web_app.repository.entity.Address;
import com.bush.pharmacy_web_app.repository.mapper.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AddressCreateMapper implements DtoMapper<AddressCreateDto, Address> {
    private final AddressRepository addressRepository;
    @Override
    public Address map(AddressCreateDto obj) {
        return copyObj(obj, new Address());
    }

    @Override
    public Address map(AddressCreateDto fromObj, Address toObj) {
        return copyObj(fromObj, toObj);
    }

    private Address copyObj(AddressCreateDto fromObj, Address toObj) {
        return Optional.ofNullable(fromObj.getId())
                .flatMap(addressRepository::findById)
                .orElseGet(() -> {
                    toObj.setSubject(fromObj.getSubject());
                    toObj.setDistrict(fromObj.getDistrict());
                    toObj.setSettlement(fromObj.getSettlement());
                    toObj.setStreet(fromObj.getStreet());
                    toObj.setHouse(fromObj.getHouse());
                    toObj.setApartment(fromObj.getApartment());
                    toObj.setPostalCode(fromObj.getPostalCode());
                    return toObj;
                });
    }
}

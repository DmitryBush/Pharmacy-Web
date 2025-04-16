package com.bush.pharmacy_web_app.repository.mapper.admin;

import com.bush.pharmacy_web_app.repository.dto.catalog.SupplierReadDto;
import com.bush.pharmacy_web_app.repository.entity.Supplier;
import com.bush.pharmacy_web_app.repository.mapper.DtoMapper;
import com.bush.pharmacy_web_app.repository.mapper.orders.AddressReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SupplierReadMapper implements DtoMapper<Supplier, SupplierReadDto> {
    private final AddressReadMapper addressReadMapper;
    @Override
    public SupplierReadDto map(Supplier obj) {
        var address = Optional.ofNullable(obj.getAddress())
                .map(addressReadMapper::map)
                .orElseThrow();
        return new SupplierReadDto(obj.getItn(), obj.getName(), address);
    }
}

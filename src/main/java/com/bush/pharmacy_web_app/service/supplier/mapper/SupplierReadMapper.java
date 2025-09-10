package com.bush.pharmacy_web_app.service.supplier.mapper;

import com.bush.pharmacy_web_app.model.dto.supplier.SupplierReadDto;
import com.bush.pharmacy_web_app.model.entity.Supplier;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
import com.bush.pharmacy_web_app.service.order.mapper.AddressReadMapper;
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

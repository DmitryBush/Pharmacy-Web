package com.bush.pharmacy_web_app.service.manufacturer.mapper;

import com.bush.pharmacy_web_app.model.dto.manufacturer.CountryReadDto;
import com.bush.pharmacy_web_app.model.dto.manufacturer.ManufacturerReadDto;
import com.bush.pharmacy_web_app.model.entity.manufacturer.Manufacturer;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ManufacturerReadMapper implements DtoMapper<Manufacturer, ManufacturerReadDto> {
    private final CountryReadMapper readMapper;
    @Override
    public ManufacturerReadDto map(Manufacturer obj) {
        var country = Optional.ofNullable(obj.getCountry())
                .map(readMapper::map)
                .map(CountryReadDto::country)
                .orElse(null);
        return new ManufacturerReadDto(obj.getId(), obj.getName(), country);
    }
}

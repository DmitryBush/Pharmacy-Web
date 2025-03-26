package com.bush.pharmacy_web_app.repository.mapper;

import com.bush.pharmacy_web_app.repository.dto.CountryReadDto;
import com.bush.pharmacy_web_app.repository.dto.ManufacturerReadDto;
import com.bush.pharmacy_web_app.repository.entity.manufacturer.Manufacturer;
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
        return new ManufacturerReadDto(obj.getName(), country);
    }
}

package com.bush.pharmacy_web_app.service.manufacturer.mapper;

import com.bush.pharmacy_web_app.model.dto.manufacturer.CountryReadDto;
import com.bush.pharmacy_web_app.model.entity.manufacturer.Country;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
import org.springframework.stereotype.Component;

@Component
public class CountryReadMapper implements DtoMapper<Country, CountryReadDto> {
    @Override
    public CountryReadDto map(Country obj) {
        return new CountryReadDto(obj.getCountry());
    }
}

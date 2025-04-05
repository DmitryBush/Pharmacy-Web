package com.bush.pharmacy_web_app.repository.mapper.manufacturer;

import com.bush.pharmacy_web_app.repository.dto.manufacturer.CountryReadDto;
import com.bush.pharmacy_web_app.repository.entity.manufacturer.Country;
import com.bush.pharmacy_web_app.repository.mapper.DtoMapper;
import org.springframework.stereotype.Component;

@Component
public class CountryReadMapper implements DtoMapper<Country, CountryReadDto> {
    @Override
    public CountryReadDto map(Country obj) {
        return new CountryReadDto(obj.getCountry());
    }
}

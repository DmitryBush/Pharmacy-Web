package com.bush.pharmacy_web_app.repository.mapper;

import com.bush.pharmacy_web_app.repository.dto.CountryCreateDto;
import com.bush.pharmacy_web_app.repository.dto.ManufacturerCreateDto;
import com.bush.pharmacy_web_app.repository.entity.manufacturer.Country;
import com.bush.pharmacy_web_app.repository.entity.manufacturer.Manufacturer;
import org.springframework.stereotype.Component;

@Component
public class CountryCreateMapper implements DtoMapper<CountryCreateDto, Country>{
    @Override
    public Country map(CountryCreateDto obj) {
        return copyObj(obj, new Country());
    }

    @Override
    public Country map(CountryCreateDto fromObj, Country toObj) {
        return copyObj(fromObj, toObj);
    }

    private Country copyObj(CountryCreateDto fromObj, Country toObj) {
        toObj.setCountry(fromObj.country());
        return toObj;
    }
}

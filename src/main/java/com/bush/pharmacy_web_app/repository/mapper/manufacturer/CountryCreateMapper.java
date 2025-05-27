package com.bush.pharmacy_web_app.repository.mapper.manufacturer;

import com.bush.pharmacy_web_app.repository.dto.manufacturer.CountryCreateDto;
import com.bush.pharmacy_web_app.repository.entity.manufacturer.Country;
import com.bush.pharmacy_web_app.repository.mapper.DtoMapper;
import com.bush.pharmacy_web_app.repository.mapper.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CountryCreateMapper implements DtoMapper<CountryCreateDto, Country> {
    private final CountryRepository countryRepository;
    @Override
    public Country map(CountryCreateDto obj) {
        return copyObj(obj, new Country());
    }

    @Override
    public Country map(CountryCreateDto fromObj, Country toObj) {
        return copyObj(fromObj, toObj);
    }

    private Country copyObj(CountryCreateDto fromObj, Country toObj) {
        return countryRepository.findByCountry(fromObj.country())
                .orElseGet(() -> {
                    toObj.setCountry(fromObj.country());
                    return toObj;
                });
    }
}

package com.bush.pharmacy_web_app.repository.mapper.manufacturer;

import com.bush.pharmacy_web_app.repository.dto.manufacturer.ManufacturerCreateDto;
import com.bush.pharmacy_web_app.repository.entity.manufacturer.Manufacturer;
import com.bush.pharmacy_web_app.repository.mapper.DtoMapper;
import com.bush.pharmacy_web_app.repository.mapper.repository.ManufacturerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ManufacturerCreateMapper implements DtoMapper<ManufacturerCreateDto, Manufacturer> {
    private final CountryCreateMapper countryCreateMapper;
    private final ManufacturerRepository manufacturerRepository;
    @Override
    public Manufacturer map(ManufacturerCreateDto obj) {
        return copyObj(obj, new Manufacturer());
    }

    @Override
    public Manufacturer map(ManufacturerCreateDto fromObj, Manufacturer toObj) {
        return copyObj(fromObj, toObj);
    }

    private Manufacturer copyObj(ManufacturerCreateDto fromObj, Manufacturer toObj) {
        return Optional.ofNullable(fromObj.id())
                .flatMap(manufacturerRepository::findById)
                .orElseGet(() -> {
                    var country = Optional.ofNullable(fromObj.country())
                            .map(countryCreateMapper::map)
                            .orElseThrow();
                    toObj.setName(fromObj.name());
                    toObj.setCountry(country);
                    return toObj;
                });
    }
}

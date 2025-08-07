package com.bush.pharmacy_web_app.service.manufacturer;

import com.bush.pharmacy_web_app.model.dto.manufacturer.CountryReadDto;
import com.bush.pharmacy_web_app.repository.manufacturer.CountryRepository;
import com.bush.pharmacy_web_app.service.manufacturer.mapper.CountryReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;
    private final CountryReadMapper countryReadMapper;

    public List<CountryReadDto> findByCountryContaining(String country) {
        return countryRepository.findByCountryContainingIgnoreCase(country)
                .stream().map(countryReadMapper::map).toList();
    }
}

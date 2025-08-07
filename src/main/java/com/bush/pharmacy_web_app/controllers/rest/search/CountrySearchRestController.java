package com.bush.pharmacy_web_app.controllers.rest.search;

import com.bush.pharmacy_web_app.model.dto.manufacturer.CountryReadDto;
import com.bush.pharmacy_web_app.service.manufacturer.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/search/country")
@RequiredArgsConstructor
public class CountrySearchRestController {
    private final CountryService countryService;

    @GetMapping
    public ResponseEntity<List<CountryReadDto>> findCountries(String searchTerm) {
        return ResponseEntity.ok(countryService.findByCountryContaining(searchTerm));
    }
}

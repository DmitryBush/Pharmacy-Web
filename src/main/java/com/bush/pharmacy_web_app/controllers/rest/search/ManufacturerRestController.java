package com.bush.pharmacy_web_app.controllers.rest.search;

import com.bush.pharmacy_web_app.repository.dto.manufacturer.ManufacturerReadDto;
import com.bush.pharmacy_web_app.repository.filter.ManufacturerFilter;
import com.bush.pharmacy_web_app.service.ManufacturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search/manufacturer")
@RequiredArgsConstructor
public class ManufacturerRestController {
    private final ManufacturerService manufacturerService;

    @GetMapping
    public List<ManufacturerReadDto> findManufacturers(ManufacturerFilter filter) {
        return manufacturerService.findAll(filter);
    }
}

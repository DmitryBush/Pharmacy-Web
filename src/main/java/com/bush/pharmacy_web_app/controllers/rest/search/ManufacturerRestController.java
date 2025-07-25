package com.bush.pharmacy_web_app.controllers.rest.search;

import com.bush.pharmacy_web_app.model.dto.manufacturer.ManufacturerReadDto;
import com.bush.pharmacy_web_app.service.manufacturer.ManufacturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/search/manufacturer")
@RequiredArgsConstructor
public class ManufacturerRestController {
    private final ManufacturerService manufacturerService;

    @GetMapping
    public List<ManufacturerReadDto> findManufacturers(String searchTerm) {
        return manufacturerService.findByNameContaining(searchTerm);
    }
}

package com.bush.pharmacy_web_app.controllers.rest.admin;

import com.bush.pharmacy_web_app.repository.dto.catalog.SupplierReadDto;
import com.bush.pharmacy_web_app.repository.filter.SupplierFilter;
import com.bush.pharmacy_web_app.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search/supplier")
@RequiredArgsConstructor
public class SupplierRestController {
    private final SupplierService service;
    @GetMapping
    public List<SupplierReadDto> findSuppliers(SupplierFilter filter) {
        return service.findAll(filter);
    }
}

package com.bush.pharmacy_web_app.controllers.rest.search;

import com.bush.pharmacy_web_app.model.dto.supplier.SupplierReadDto;
import com.bush.pharmacy_web_app.service.supplier.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/search/supplier")
@RequiredArgsConstructor
public class SupplierRestController {
    private final SupplierService service;
    @GetMapping
    public List<SupplierReadDto> findSuppliers(String searchTerm) {
        return service.findByNameContaining(searchTerm);
    }
}

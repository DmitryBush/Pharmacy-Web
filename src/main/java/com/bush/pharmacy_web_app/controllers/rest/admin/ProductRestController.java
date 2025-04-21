package com.bush.pharmacy_web_app.controllers.rest.admin;

import com.bush.pharmacy_web_app.repository.dto.admin.MedicineAdminReadDto;
import com.bush.pharmacy_web_app.repository.dto.catalog.MedicineCreateDto;
import com.bush.pharmacy_web_app.repository.dto.orders.MedicineReadDto;
import com.bush.pharmacy_web_app.repository.filter.MedicineFilter;
import com.bush.pharmacy_web_app.service.MedicineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/admin/product")
@RequiredArgsConstructor
public class ProductRestController {
    private final MedicineService medicineService;

    @GetMapping
    public HttpEntity<PagedModel<EntityModel<MedicineReadDto>>> findAllProducts(MedicineFilter medicineFilter,
                                                                                @PageableDefault(size = 15, sort = "price",
                                                                                       direction = Sort.Direction.ASC)
                                                                               Pageable pageable,
                                                                                PagedResourcesAssembler<MedicineReadDto> assembler) {
        return ResponseEntity.ok(assembler.toModel(medicineService.findAll(medicineFilter, pageable)));
    }

    @GetMapping("/{id}")
    public MedicineAdminReadDto findByIdProduct(@PathVariable Long id) {
        return medicineService.findAdminDtoById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public MedicineReadDto createProduct(@RequestBody @Validated MedicineCreateDto medicineCreateDto) {
        return medicineService.createMedicine(medicineCreateDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public MedicineReadDto updateProduct(@PathVariable Long id,
                                         @RequestBody @Valid MedicineCreateDto medicineCreateDto) {
        return medicineService.updateMedicine(id, medicineCreateDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteProduct(@PathVariable Long id) {
        if (!medicineService.deleteMedicine(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}

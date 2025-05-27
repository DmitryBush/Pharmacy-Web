package com.bush.pharmacy_web_app.controllers.rest.admin;

import com.bush.pharmacy_web_app.repository.dto.medicine.MedicineAdminReadDto;
import com.bush.pharmacy_web_app.repository.dto.medicine.MedicineCreateDto;
import com.bush.pharmacy_web_app.repository.dto.medicine.MedicinePreviewReadDto;
import com.bush.pharmacy_web_app.repository.filter.MedicineFilter;
import com.bush.pharmacy_web_app.service.MedicineService;
import com.bush.pharmacy_web_app.validation.ImageFile;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/admin/product")
@RequiredArgsConstructor
public class ProductRestController {
    private final MedicineService medicineService;

    @GetMapping
    public HttpEntity<PagedModel<EntityModel<MedicinePreviewReadDto>>> findAllProducts(MedicineFilter medicineFilter,
                                                                                       @PageableDefault(size = 15, sort = "price",
                                                                                       direction = Sort.Direction.ASC)
                                                                               Pageable pageable,
                                                                                       PagedResourcesAssembler<MedicinePreviewReadDto> assembler) {
        return ResponseEntity.ok(assembler.toModel(medicineService.findAllPreviews(medicineFilter, pageable)));
    }

    @GetMapping("/{id}")
    public MedicineAdminReadDto findByIdProduct(@PathVariable Long id) {
        return medicineService.findAdminDtoById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public MedicinePreviewReadDto createProduct(@RequestPart("product") @Validated MedicineCreateDto medicineCreateDto,
                                                @RequestPart(value = "images", required = false)
                                         @Validated @ImageFile({"image/jpeg", "image/png", "image/webp"})
                                         List<MultipartFile> images) {
        return medicineService.createMedicine(medicineCreateDto, images)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public MedicinePreviewReadDto updateProduct(@PathVariable Long id,
                                                @RequestPart("product") @Validated MedicineCreateDto medicineCreateDto,
                                                @RequestPart(value = "images", required = false)
                                             @Validated @NotNull @ImageFile({"image/jpeg", "image/png", "image/webm"})
                                             List<MultipartFile> images) {
        return medicineService.updateMedicine(id, medicineCreateDto, images)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteProduct(@PathVariable Long id) {
        if (!medicineService.deleteMedicine(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}

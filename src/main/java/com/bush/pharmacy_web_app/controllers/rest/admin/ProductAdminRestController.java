package com.bush.pharmacy_web_app.controllers.rest.admin;

import com.bush.pharmacy_web_app.model.dto.product.ProductAdminReadDto;
import com.bush.pharmacy_web_app.model.dto.product.ProductCreateDto;
import com.bush.pharmacy_web_app.model.dto.product.ProductPreviewReadDto;
import com.bush.pharmacy_web_app.repository.product.filter.ProductFilter;
import com.bush.pharmacy_web_app.service.product.ProductService;
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
@RequestMapping("/api/v1/admin/products")
@RequiredArgsConstructor
public class ProductAdminRestController {
    private final ProductService productService;

    @GetMapping
    public HttpEntity<PagedModel<EntityModel<ProductPreviewReadDto>>> findAllProducts(ProductFilter productFilter,
                                                                                      @PageableDefault(size = 15, sort = "price",
                                                                                       direction = Sort.Direction.ASC)
                                                                               Pageable pageable,
                                                                                      PagedResourcesAssembler<ProductPreviewReadDto> assembler) {
        return ResponseEntity.ok(assembler.toModel(productService.findAllPreviews(productFilter, pageable)));
    }

    @GetMapping("/{id}")
    public ProductAdminReadDto findByIdProduct(@PathVariable Long id) {
        return productService.findAdminDtoById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ProductPreviewReadDto createProduct(@RequestPart("product") @Validated ProductCreateDto productCreateDto,
                                               @RequestPart(value = "images", required = false)
                                         @Validated @ImageFile({"image/jpeg", "image/png", "image/webp"})
                                         List<MultipartFile> images) {
        return productService.createMedicine(productCreateDto, images);
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public ProductPreviewReadDto updateProduct(@PathVariable Long id,
                                               @RequestPart("product") @Validated ProductCreateDto productCreateDto,
                                               @RequestPart(value = "images", required = false)
                                             @Validated @NotNull @ImageFile({"image/jpeg", "image/png", "image/webm"})
                                             List<MultipartFile> images) {
        return productService.updateMedicine(id, productCreateDto, images);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteProduct(@PathVariable Long id) {
        if (!productService.deleteMedicine(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}

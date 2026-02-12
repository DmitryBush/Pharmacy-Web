package com.bush.pharmacy_web_app.controllers.rest.product;

import com.bush.pharmacy_web_app.model.dto.medicine.MedicinePreviewReadDto;
import com.bush.pharmacy_web_app.repository.medicine.filter.MedicineFilter;
import com.bush.pharmacy_web_app.service.branch.TransactionService;
import com.bush.pharmacy_web_app.service.medicine.MedicineService;
import com.bush.pharmacy_web_app.service.medicine.dailyfeatured.DailyFeaturedProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductRestController {
    private final DailyFeaturedProductService dailyFeaturedProductService;
    private final TransactionService transactionService;
    private final MedicineService medicineService;

    @GetMapping("/daily-products")
    public List<MedicinePreviewReadDto> getDailyProducts() {
        return dailyFeaturedProductService.findDailyMedicine();
    }

    @GetMapping("/best-sellers")
    public List<MedicinePreviewReadDto> getBestSellingProducts() {
        return transactionService.findBestSellingProducts();
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<MedicinePreviewReadDto>>> getProductsByFilter(Pageable pageable,
                                                                                               MedicineFilter filter,
                                                                                               PagedResourcesAssembler<MedicinePreviewReadDto> assembler) {
        return ResponseEntity.ok(assembler.toModel(medicineService.findAllPreviews(filter, pageable)));
    }
}

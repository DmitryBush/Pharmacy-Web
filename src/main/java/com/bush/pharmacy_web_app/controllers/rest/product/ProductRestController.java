package com.bush.pharmacy_web_app.controllers.rest.product;

import com.bush.pharmacy_web_app.model.dto.medicine.MedicinePreviewReadDto;
import com.bush.pharmacy_web_app.service.medicine.dailyfeatured.DailyFeaturedProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductRestController {
    private final DailyFeaturedProductService dailyFeaturedProductService;

    @GetMapping("/daily-products")
    public List<MedicinePreviewReadDto> getDailyProducts() {
        return dailyFeaturedProductService.findDailyMedicine();
    }
}

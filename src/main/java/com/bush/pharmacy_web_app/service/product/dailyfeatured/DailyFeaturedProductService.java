package com.bush.pharmacy_web_app.service.product.dailyfeatured;

import com.bush.pharmacy_web_app.model.dto.product.ProductPreviewReadDto;
import com.bush.pharmacy_web_app.model.entity.product.dailyfeatured.DailyFeaturedProduct;
import com.bush.pharmacy_web_app.repository.product.ProductRepository;
import com.bush.pharmacy_web_app.repository.product.dailyfeatured.DailyFeaturedProductsRepository;
import com.bush.pharmacy_web_app.service.product.mapper.MedicinePreviewReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@CacheConfig(cacheNames = "DailyFeaturedProduct")
public class DailyFeaturedProductService {
    private final DailyFeaturedProductsRepository productsRepository;
    private final ProductRepository productRepository;

    private final DailyFeaturedProductChangelogService changelogService;

    private final MedicinePreviewReadMapper medicinePreviewReadMapper;

    @CacheEvict
    @Transactional
    public void createDailyFeaturedProducts(List<ProductPreviewReadDto> products) {
        if (products == null) {
            throw new NullPointerException("Products should not be null");
        }

        var distinctProducts = products.stream().distinct().toList();
        if (distinctProducts.size() < products.size()) {
            throw new IllegalArgumentException("Duplicates found in products");
        }

        var featuredProducts = IntStream.range(0, distinctProducts.size())
                .mapToObj(identifier -> new DailyFeaturedProduct((short) identifier,
                        productRepository.getReferenceById(distinctProducts.get(identifier).id())))
                .toList();

        productsRepository.deleteAllInBatch();
        productsRepository.saveAll(featuredProducts);
        changelogService.createLog();
    }

    @CacheEvict
    @Transactional
    public void deleteAllDailyFeaturedProducts() {
        productsRepository.deleteAll();
    }

    @Cacheable
    public List<ProductPreviewReadDto> findDailyMedicine() {
        return productsRepository.findAll()
                .stream()
                .map(DailyFeaturedProduct::getProduct)
                .map(medicinePreviewReadMapper::map)
                .collect(Collectors.toList());
    }
}

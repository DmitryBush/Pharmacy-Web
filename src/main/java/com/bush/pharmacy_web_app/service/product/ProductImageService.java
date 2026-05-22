package com.bush.pharmacy_web_app.service.product;

import com.bush.pharmacy_web_app.model.dto.product.ProductImageReadDto;
import com.bush.pharmacy_web_app.model.entity.product.Product;
import com.bush.pharmacy_web_app.model.entity.product.ProductImage;
import com.bush.pharmacy_web_app.repository.product.ProductImageRepository;
import com.bush.pharmacy_web_app.service.product.mapper.ProductImageCreateMapper;
import com.bush.pharmacy_web_app.service.product.mapper.ProductImageReadMapper;
import com.bush.pharmacy_web_app.service.storage.BucketConstantEnum;
import com.bush.pharmacy_web_app.service.storage.ObjectStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductImageService {
    private final ProductImageRepository imageRepository;
    private final ObjectStorageService storageService;

    private final ProductImageCreateMapper imageCreateMapper;
    private final ProductImageReadMapper imageReadMapper;

    private List<ProductImageReadDto> findProductImageListByMedicineId(Long id) {
        return imageRepository.findByProductId(id)
                .stream()
                .map(imageReadMapper::mapToMedicineImageReadDto)
                .toList();
    }

    public Resource findImageById(Long id) {
        return storageService.loadResource(BucketConstantEnum.PRODUCT, findProductImageById(id).getPath());
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public ProductImage createImage(MultipartFile file, Product product) {
        ProductImage image = Optional.ofNullable(product)
                .map(imageCreateMapper::mapToProductImage)
                .map(imageRepository::save)
                .orElseThrow(() -> new IllegalArgumentException("Product id can't be null"));

        storageService.store(file, BucketConstantEnum.PRODUCT, image.getPath());
        product.addImage(image);
        return image;
    }

    @CacheEvict(cacheNames = {"productImage", "productImage:path"}, key = "id")
    @Transactional
    public void deleteImage(Long id) {
        imageRepository.findById(id)
                .ifPresentOrElse(image -> {
                    imageRepository.delete(image);
                    storageService.delete(BucketConstantEnum.PRODUCT, image.getPath());
                }, () -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                });
    }

    protected void deleteImage(ProductImage productImage) {
        storageService.delete(BucketConstantEnum.PRODUCT, productImage.getPath());
    }

    public Resource findProductImageByIdAndName(Long id, String filename) {
        return storageService.loadResource(BucketConstantEnum.PRODUCT, findImagePathByMedicineIdAndPath(id, filename));
    }

    @Cacheable(cacheNames = "productImage", key = "#id")
    private ProductImage findProductImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Cacheable(cacheNames = "productImage:path", key = "#id + #filename")
    private String findImagePathByMedicineIdAndPath(Long id, String filename) {
        return imageRepository.findByProductIdAndPath(id, filename)
                .map(ProductImage::getPath)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}

package com.bush.pharmacy_web_app.service.product;

import com.bush.pharmacy_web_app.model.dto.product.ProductImageReadDto;
import com.bush.pharmacy_web_app.model.entity.product.Product;
import com.bush.pharmacy_web_app.model.entity.product.ProductImage;
import com.bush.pharmacy_web_app.repository.product.ProductImageRepository;
import com.bush.pharmacy_web_app.service.filesystem.FileSystemStorageService;
import com.bush.pharmacy_web_app.service.product.mapper.ProductImageCreateMapper;
import com.bush.pharmacy_web_app.service.product.mapper.ProductImageReadMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final FileSystemStorageService storageService;

    private final ProductImageCreateMapper imageCreateMapper;
    private final ProductImageReadMapper imageReadMapper;

    private List<ProductImageReadDto> findProductImageListByMedicineId(Long id) {
        return imageRepository.findByProductId(id)
                .stream()
                .map(imageReadMapper::mapToMedicineImageReadDto)
                .toList();
    }

    public Optional<Resource> findImageById(Long id) {
        return imageRepository.findById(id)
                .map(ProductImage::getPath)
                .map(storageService::loadAsResource);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public ProductImage createImage(MultipartFile file, Product product) {
        ProductImage image = Optional.ofNullable(product)
                .map(imageCreateMapper::mapToProductImage)
                .map(imageRepository::save)
                .orElseThrow(() -> new IllegalArgumentException("Product id can't be null"));

        storageService.storeByFullPath(file, image.getPath());
        product.addImage(image);
        return image;
    }

    @Transactional
    public void deleteImage(Long id) {
        imageRepository.findById(id)
                .ifPresentOrElse(image -> {
                    imageRepository.delete(image);
                    storageService.delete(image.getPath());
                }, () -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                });
    }

    protected void deleteImage(ProductImage productImage) {
        storageService.delete(productImage.getPath());
    }

    public Optional<Resource> findProductImageByIdAndName(Long id, String filename) {
        return findImageByMedicineIdAndPath(id, filename)
                .map(storageService::loadAsResource);
    }

    private Optional<String> findImageByMedicineIdAndPath(Long id, String filename) {
        return imageRepository.findByProductIdAndPath(id, filename)
                .map(ProductImage::getPath);
    }
}

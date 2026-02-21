package com.bush.search.service.product;

import com.bush.search.domain.dto.ProductPreviewDto;
import com.bush.search.domain.index.product.ProductPayload;
import com.bush.search.repository.ProductRepository;
import com.bush.search.service.product.mapper.ProductCreateMapper;
import com.bush.search.service.product.mapper.ProductReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    private final ProductCreateMapper productCreateMapper;
    private final ProductReadMapper productReadMapper;

    public void createProduct(ProductPayload productPayload) {
        Optional.ofNullable(productPayload)
                .map(productCreateMapper::mapToProduct)
                .map(productRepository::save);
    }

    public void updateProduct(String id, ProductPayload productPayload) {
        Optional.ofNullable(id)
                .flatMap(productRepository::findById)
                .map(_ -> productCreateMapper.mapToProduct(productPayload))
                .map(productRepository::save);
    }

    public void deleteProduct(String id) {
        Optional.ofNullable(id)
                .flatMap(productRepository::findById)
                .ifPresent(productRepository::delete);
    }

    public List<ProductPreviewDto> findProductsByName(String productName) {
        return productRepository.findByNameContainingIgnoreCase(productName).stream()
                .map(productReadMapper::mapToProductPreviewDto)
                .toList();
    }
}

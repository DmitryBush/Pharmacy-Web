package com.bush.pharmacy_web_app.service.product;

import com.bush.pharmacy_web_app.model.entity.product.ProductType;
import com.bush.pharmacy_web_app.model.entity.product.Product;
import com.bush.pharmacy_web_app.model.entity.product.ProductTypeMapping;
import com.bush.pharmacy_web_app.model.entity.product.ProductTypeMappingId;
import com.bush.pharmacy_web_app.repository.product.ProductTypeMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.MANDATORY)
class ProductTypeMappingService {
    private final ProductTypeMappingRepository mappingRepository;

    protected void createProductTypeMapping(Product product, ProductType type, Boolean isMainType) {
        ProductTypeMappingId id = new ProductTypeMappingId(product, type);
        ProductTypeMapping mapping = new ProductTypeMapping(id, isMainType);
        mapping = mappingRepository.save(mapping);
        product.addType(mapping);
    }
}

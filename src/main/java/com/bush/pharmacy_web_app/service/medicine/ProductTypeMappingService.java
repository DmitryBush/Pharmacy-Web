package com.bush.pharmacy_web_app.service.medicine;

import com.bush.pharmacy_web_app.model.entity.medicine.MedicineType;
import com.bush.pharmacy_web_app.model.entity.medicine.Product;
import com.bush.pharmacy_web_app.model.entity.medicine.ProductTypeMapping;
import com.bush.pharmacy_web_app.model.entity.medicine.ProductTypeMappingId;
import com.bush.pharmacy_web_app.repository.medicine.ProductTypeMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.MANDATORY)
class ProductTypeMappingService {
    private final ProductTypeMappingRepository mappingRepository;

    protected void createProductTypeMapping(Product product, MedicineType type, Boolean isMainType) {
        ProductTypeMappingId id = new ProductTypeMappingId(product, type);
        ProductTypeMapping mapping = new ProductTypeMapping(id, isMainType);
        mapping = mappingRepository.save(mapping);
        product.addType(mapping);
    }
}

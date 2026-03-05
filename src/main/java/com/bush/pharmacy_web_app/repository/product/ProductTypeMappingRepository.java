package com.bush.pharmacy_web_app.repository.product;

import com.bush.pharmacy_web_app.model.entity.product.Product;
import com.bush.pharmacy_web_app.model.entity.product.ProductType;
import com.bush.pharmacy_web_app.model.entity.product.ProductTypeMapping;
import com.bush.pharmacy_web_app.model.entity.product.ProductTypeMappingId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTypeMappingRepository extends JpaRepository<ProductTypeMapping, ProductTypeMappingId> {
    boolean existsByIdProductAndIdType(Product product, ProductType type);
}

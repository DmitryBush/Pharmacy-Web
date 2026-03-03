package com.bush.pharmacy_web_app.repository.medicine;

import com.bush.pharmacy_web_app.model.entity.medicine.ProductTypeMapping;
import com.bush.pharmacy_web_app.model.entity.medicine.ProductTypeMappingId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTypeMappingRepository extends JpaRepository<ProductTypeMapping, ProductTypeMappingId> {
}

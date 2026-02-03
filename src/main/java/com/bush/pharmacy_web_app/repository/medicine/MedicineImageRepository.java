package com.bush.pharmacy_web_app.repository.medicine;

import com.bush.pharmacy_web_app.model.entity.medicine.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicineImageRepository extends JpaRepository<ProductImage, Long> {
    Optional<ProductImage> findByProductIdAndPath(Long id, String path);

    List<ProductImage> findByProductId(Long id);
}

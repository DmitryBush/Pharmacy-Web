package com.bush.pharmacy_web_app.repository.medicine;

import com.bush.pharmacy_web_app.model.entity.medicine.MedicineImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicineImageRepository extends JpaRepository<MedicineImage, Long> {
    Optional<MedicineImage> findByMedicineIdAndPath(Long id, String path);

    List<MedicineImage> findByMedicineId(Long id);
}

package com.bush.pharmacy_web_app.repository.medicine;

import com.bush.pharmacy_web_app.model.entity.medicine.MedicineType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypeRepository extends JpaRepository<MedicineType, Integer> {
    Optional<MedicineType> findByType(String type);
}

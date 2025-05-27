package com.bush.pharmacy_web_app.repository;

import com.bush.pharmacy_web_app.repository.entity.medicine.MedicineType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypeRepository extends JpaRepository<MedicineType, Integer> {
    Optional<MedicineType> findByType(String type);
}

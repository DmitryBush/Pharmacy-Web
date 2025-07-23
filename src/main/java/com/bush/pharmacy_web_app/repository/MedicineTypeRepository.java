package com.bush.pharmacy_web_app.repository;

import com.bush.pharmacy_web_app.repository.entity.medicine.MedicineType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MedicineTypeRepository extends JpaRepository<MedicineType, Integer> {
    Optional<MedicineType> findByType(String type);

    @Query("select distinct mt from MedicineType mt")
    List<MedicineType> findAllDistinctTypes();
}

package com.bush.pharmacy_web_app.repository;

import com.bush.pharmacy_web_app.repository.entity.StorageItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StorageRepository extends JpaRepository<StorageItems, Long> {
    List<StorageItems> findByBranchId(Integer id);

    List<StorageItems> findByBranchIdAndMedicineId(Integer branchId, Long medicineId);
}

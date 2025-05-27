package com.bush.pharmacy_web_app.repository;

import com.bush.pharmacy_web_app.repository.entity.PharmacyBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PharmacyBranchRepository extends JpaRepository<PharmacyBranch, Long> {
    @Query("select b from PharmacyBranch b " +
            "join StorageItems s on b = s.branch " +
            "where s.medicine.id = :id")
    List<PharmacyBranch> findBranchesWithMedicineLocated(@Param("id") Long id);
}

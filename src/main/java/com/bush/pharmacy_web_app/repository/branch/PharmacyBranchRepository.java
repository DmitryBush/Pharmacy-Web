package com.bush.pharmacy_web_app.repository.branch;

import com.bush.pharmacy_web_app.model.entity.branch.PharmacyBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PharmacyBranchRepository extends JpaRepository<PharmacyBranch, Long> {
    @Query("select b from PharmacyBranch b " +
            "join StorageItems s on b = s.branch " +
            "where s.product.id = :id")
    List<PharmacyBranch> findBranchesWithMedicineLocated(@Param("id") Long id);

    @Query("select b from PharmacyBranch b " +
            "join BranchUserAssignment ba on b = ba.id.branchId " +
            "where ba.id.userId.id = :userId")
    List<PharmacyBranch> findUserAssignedBranches(@Param("userId") String userId);
}

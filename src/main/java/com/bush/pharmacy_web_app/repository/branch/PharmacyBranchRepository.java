package com.bush.pharmacy_web_app.repository.branch;

import com.bush.pharmacy_web_app.model.entity.branch.PharmacyBranch;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PharmacyBranchRepository extends JpaRepository<PharmacyBranch, Long> {
    @Query("select b from PharmacyBranch b " +
            "join StorageItems s on b = s.branch " +
            "where s.product.id = :id")
    List<PharmacyBranch> findBranchesWithMedicineLocated(@Param("id") Long id);

    @Query("select b from PharmacyBranch b " +
            "join BranchUserAssignment ba on b = ba.id.branch " +
            "where ba.id.user.id = :userId")
    List<PharmacyBranch> findUserAssignedBranches(@Param("userId") String userId);

    @Query("select b from PharmacyBranch b " +
            "join fetch b.address " +
            "left join fetch b.supervisor " +
            "join fetch b.openingHours " +
            "where b.id = :branchId")
    Optional<PharmacyBranch> findBranchInfoById(@Param("branchId") Long branchId);
}

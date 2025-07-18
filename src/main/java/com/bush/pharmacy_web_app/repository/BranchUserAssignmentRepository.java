package com.bush.pharmacy_web_app.repository;

import com.bush.pharmacy_web_app.repository.entity.branch.BranchUserAssignment;
import com.bush.pharmacy_web_app.repository.entity.branch.BranchUserAssignmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BranchUserAssignmentRepository extends JpaRepository<BranchUserAssignment, BranchUserAssignmentId> {
    @Query("select exists(select bu from BranchUserAssignment bu " +
            "where bu.id.userId.id = :userId and bu.id.branchId.id = :branchId)")
    Boolean checkUserBranchAccess(@Param("userId") String userId, @Param("branchId") Long branchId);
}

package com.bush.pharmacy_web_app.repository.branch;

import com.bush.pharmacy_web_app.model.entity.branch.BranchUserAssignment;
import com.bush.pharmacy_web_app.model.entity.branch.BranchUserAssignmentId;
import com.bush.pharmacy_web_app.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BranchUserAssignmentRepository extends JpaRepository<BranchUserAssignment, BranchUserAssignmentId> {
    @Query("select exists(select bu from BranchUserAssignment bu " +
            "where bu.id.user.id = :userId and bu.id.branch.id = :branchId)")
    Boolean checkUserBranchAccess(@Param("userId") String userId, @Param("branchId") Long branchId);
    @Query("select u from BranchUserAssignment bu " +
            "join User u on bu.id.user = u " +
            "where bu.id.branch.id = :branchId")
    List<User> findAssignedUsersByBranchId(@Param("branchId") Long id);
}

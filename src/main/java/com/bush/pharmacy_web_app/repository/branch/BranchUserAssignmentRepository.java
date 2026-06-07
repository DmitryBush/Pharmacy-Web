package com.bush.pharmacy_web_app.repository.branch;

import com.bush.pharmacy_web_app.model.entity.branch.BranchUserAssignment;
import com.bush.pharmacy_web_app.model.entity.branch.BranchUserAssignmentId;
import com.bush.pharmacy_web_app.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BranchUserAssignmentRepository extends JpaRepository<BranchUserAssignment, BranchUserAssignmentId> {
    @Query("select exists(select bu from BranchUserAssignment bu " +
            "where bu.user.id = :userId and bu.branch.id = :branchId)")
    Boolean checkUserBranchAccess(@Param("userId") String userId, @Param("branchId") Long branchId);
    @Query("select u from BranchUserAssignment bu " +
            "join bu.user u " +
            "join fetch u.role r " +
            "where bu.branch.id = :branchId")
    List<User> findAssignedUsersByBranchId(@Param("branchId") Long id);
    @Query("select bua from BranchUserAssignment bua " +
            "join fetch bua.user u " +
            "join fetch u.role r " +
            "join fetch bua.branch b " +
            "where u.id = :userId and b.id = :branchId")
    Optional<BranchUserAssignment> findAssignmentByUserIdAndBranchId(@Param("branchId") Long branchId,
                                                                     @Param("userId") String userId);
}

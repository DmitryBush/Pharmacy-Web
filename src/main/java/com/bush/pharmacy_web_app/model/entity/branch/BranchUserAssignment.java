package com.bush.pharmacy_web_app.model.entity.branch;

import com.bush.pharmacy_web_app.model.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "branch_user_assignment")
public class BranchUserAssignment {
    @EmbeddedId
    private BranchUserAssignmentId id;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @MapsId("userId")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @MapsId("branchId")
    @ManyToOne
    @JoinColumn(name = "branch_id")
    private PharmacyBranch branch;
}

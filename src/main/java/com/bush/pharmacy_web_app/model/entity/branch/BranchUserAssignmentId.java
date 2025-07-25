package com.bush.pharmacy_web_app.model.entity.branch;

import com.bush.pharmacy_web_app.model.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@IdClass(BranchUserAssignmentId.class)
public class BranchUserAssignmentId {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;
    @ManyToOne
    @JoinColumn(name = "branch_id")
    private PharmacyBranch branchId;
}

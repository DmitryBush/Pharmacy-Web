package com.bush.pharmacy_web_app.model.entity.branch;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class BranchUserAssignmentId implements Serializable {
    private String userId;
    private Long branchId;
}

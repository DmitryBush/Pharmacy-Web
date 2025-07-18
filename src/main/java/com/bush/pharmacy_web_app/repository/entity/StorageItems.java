package com.bush.pharmacy_web_app.repository.entity;

import com.bush.pharmacy_web_app.repository.entity.branch.PharmacyBranch;
import com.bush.pharmacy_web_app.repository.entity.medicine.Medicine;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "storage")
public class StorageItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id", nullable = false)
    private Long id;
    private Integer amount;
    @OneToOne
    @JoinColumn(name = "f_key_medicine_id", nullable = false, unique = true)
    private Medicine medicine;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_key_branch_id", nullable = false)
    private PharmacyBranch branch;
}

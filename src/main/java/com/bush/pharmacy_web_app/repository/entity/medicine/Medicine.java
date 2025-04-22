package com.bush.pharmacy_web_app.repository.entity.medicine;

import com.bush.pharmacy_web_app.repository.entity.Supplier;
import com.bush.pharmacy_web_app.repository.entity.manufacturer.Manufacturer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "medicine")
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medicine_id", nullable = false)
    private Long id;
    @Column(name = "medicine_name", nullable = false)
    private String name;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "f_key_medicine_type", nullable = false)
    private MedicineType type;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "fk_medicine_manufacturer")
    private Manufacturer manufacturer;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private Boolean recipe;

    @Column(name = "active_ingredient", nullable = false, length = 25)
    private String activeIngredient;
    @Column(name = "expiration", nullable = false, length = 25)
    private String expirationDate;
    private String composition;
    @Column(name = "indications")
    private String indication;
    private String contraindications;
    @Column(name = "side_effects")
    private String sideEffect;
    private String interaction;
    @Column(name = "admission_course")
    private String admissionCourse;
    private String overdose;
    @Column(name = "special_instructions")
    private String specialInstruction;
    @Column(name = "storage_conditions")
    private String storageCondition;
    @Column(name = "release_form")
    private String releaseForm;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "f_key_supplier_itn", nullable = false)
    private Supplier supplier;
}

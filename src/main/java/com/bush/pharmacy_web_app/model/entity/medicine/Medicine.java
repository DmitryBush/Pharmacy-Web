package com.bush.pharmacy_web_app.model.entity.medicine;

import com.bush.pharmacy_web_app.model.entity.Supplier;
import com.bush.pharmacy_web_app.model.entity.manufacturer.Manufacturer;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"image", "supplier"})
@Entity
@Table(name = "medicine")
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medicine_id", nullable = false)
    private Long id;
    @Column(name = "medicine_name", nullable = false)
    private String name;
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "id.medicine")
    private List<ProductCategories> type;
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

    @Builder.Default
    @OneToMany(mappedBy = "medicine", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicineImage> image = new ArrayList<>();

    public void setImage(List<MedicineImage> image) {
        if (image != null) {
            this.image.clear();
            this.image.addAll(image);
        }
    }
}

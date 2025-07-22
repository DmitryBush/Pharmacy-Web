package com.bush.pharmacy_web_app.repository.entity.medicine;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "medicine_types")
public class MedicineType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id", nullable = false)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String type;
    @OneToOne
    @JoinColumn(name = "parent_id")
    private MedicineType parentId;

    @OneToMany(mappedBy = "parentId")
    private List<MedicineType> childTypes;
}

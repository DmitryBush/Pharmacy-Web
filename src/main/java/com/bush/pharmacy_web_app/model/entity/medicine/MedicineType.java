package com.bush.pharmacy_web_app.model.entity.medicine;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_types")
public class MedicineType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id", nullable = false)
    private Integer id;
    @Column(name = "type_name", nullable = false, unique = true, length = 64)
    private String name;
    @Column(name = "type_slug", nullable = false, unique = true, length = 64)
    private String slug;
    @OneToOne
    @JoinColumn(name = "parent_id")
    @JsonIgnore
    private MedicineType parent;

    @OneToMany(mappedBy = "parent")
    @JsonIgnore
    private List<MedicineType> childTypes;
}

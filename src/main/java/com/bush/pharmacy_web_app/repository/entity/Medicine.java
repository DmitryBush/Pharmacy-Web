package com.bush.pharmacy_web_app.repository.entity;

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
    private Integer id;
    @Column(name = "medicine_name", nullable = false)
    private String name;
    @Column(name = "medicine_type", nullable = false)
    private String type;
    @ManyToOne
    @JoinColumn(name = "fk_medicine_manufacturer")
    private Manufacturer manufacturer;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private Boolean recipe;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_key_supplier_itn")
    private Supplier supplier;
}

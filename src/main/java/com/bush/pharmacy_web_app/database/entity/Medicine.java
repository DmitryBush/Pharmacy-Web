package com.bush.pharmacy_web_app.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
    @Column(name = "medicine_manufacturer", nullable = false)
    private String manufacturer;
    @Column(nullable = false)
    private Long price;
    @Column(nullable = false)
    private Boolean recipe;
    @ManyToOne
    @JoinColumn(name = "f_key_supplier_itn")
    private Supplier supplier;
}

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
@Table(name = "suppliers")
public class Supplier {
    @Id
    @Column(nullable = false)
    private String itn;
    @Column(name = "supplier_name", nullable = false)
    private String name;
    @OneToOne
    @JoinColumn(name = "f_key_address_id", nullable = false)
    private Address address;

    @Builder.Default
    @OneToMany(mappedBy = "supplier")
    private List<Medicine> medicines = new ArrayList<>();
}

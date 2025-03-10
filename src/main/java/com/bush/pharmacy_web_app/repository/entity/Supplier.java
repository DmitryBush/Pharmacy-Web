package com.bush.pharmacy_web_app.repository.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "medicines")
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

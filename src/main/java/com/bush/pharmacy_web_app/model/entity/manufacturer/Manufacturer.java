package com.bush.pharmacy_web_app.model.entity.manufacturer;

import com.bush.pharmacy_web_app.model.entity.medicine.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "manufacturers")
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manufacturer_id", nullable = false)
    private Long id;
    @Column(name = "manufacturer_name", nullable = false, length = 64)
    private String name;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "f_key_country_id", nullable = false)
    private Country country;

    @OneToMany(mappedBy = "manufacturer")
    private List<Product> products = new ArrayList<>();
}

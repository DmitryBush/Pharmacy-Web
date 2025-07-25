package com.bush.pharmacy_web_app.model.entity;

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
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", nullable = false)
    private Long id;
    @Column(nullable = false)
    private String subject;
    private String district;
    @Column(nullable = false)
    private String settlement;
    @Column(nullable = false)
    private String street;
    @Column(nullable = false)
    private String house;
    @Column(nullable = false)
    private String apartment;
    @Column(name = "postal_code", nullable = false)
    private String postalCode;
}

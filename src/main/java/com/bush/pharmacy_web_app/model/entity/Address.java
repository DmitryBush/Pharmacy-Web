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
    @Column(nullable = false, length = 128)
    private String subject;
    @Column(length = 128)
    private String district;
    @Column(nullable = false, length = 128)
    private String settlement;
    @Column(nullable = false, length = 256)
    private String street;
    @Column(nullable = false, length = 32)
    private String house;
    @Column(nullable = false, length = 10)
    private String apartment;
    @Column(name = "postal_code", nullable = false, length = 6)
    private String postalCode;
}

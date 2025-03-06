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
@Table(name = "customers")
public class Customer {
    @Id
    @Column(name = "mobile_phone", nullable = false)
    private String mobilePhone;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Column(name = "last_name")
    private String lastName;

    @Builder.Default
    @OneToMany(mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();
}

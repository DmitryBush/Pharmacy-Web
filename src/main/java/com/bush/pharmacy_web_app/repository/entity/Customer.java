package com.bush.pharmacy_web_app.repository.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"orders"})
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
    @Column(nullable = false)
    private String password;

    @Builder.Default
    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE)
    private List<Order> orders = new ArrayList<>();
}

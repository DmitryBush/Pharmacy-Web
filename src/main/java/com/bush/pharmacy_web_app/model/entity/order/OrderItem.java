package com.bush.pharmacy_web_app.model.entity.order;

import com.bush.pharmacy_web_app.model.entity.medicine.Medicine;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "obj_id")
    private Long id;
    @Column(name = "obj_amount")
    private Integer amount;
    @Column(name = "obj_price", nullable = false)
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "f_key_medicine_id", nullable = false)
    private Medicine medicine;
    @ManyToOne
    @JoinColumn(name = "f_key_order_id", nullable = false)
    private Order order;
}

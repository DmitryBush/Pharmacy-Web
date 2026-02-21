package com.bush.pharmacy_web_app.model.entity.medicine.dailyfeatured;

import com.bush.pharmacy_web_app.model.entity.medicine.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "daily_featured_products")
public class DailyFeaturedProduct {
    @Id
    private Short id;
    @OneToOne
    @JoinColumn(name = "product_id", nullable = false, unique = true)
    private Product product;
}

package com.bush.pharmacy_web_app.repository.entity.medicine;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Builder
@IdClass(ProductCategoriesId.class)
public class ProductCategoriesId {
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Medicine medicine;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private MedicineType type;
}

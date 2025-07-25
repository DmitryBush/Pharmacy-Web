package com.bush.pharmacy_web_app.model.entity.medicine;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_categories")
public class ProductCategories {
    @EmbeddedId
    private ProductCategoriesId id;

    @Column(name = "is_main", nullable = false)
    private Boolean isMain;
}

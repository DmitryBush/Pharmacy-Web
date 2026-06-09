package com.bush.pharmacy_web_app.model.entity.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Builder
@IdClass(ProductTypeMappingId.class)
public class ProductTypeMappingId implements Serializable {
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    @JsonUnwrapped
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private ProductType type;
}

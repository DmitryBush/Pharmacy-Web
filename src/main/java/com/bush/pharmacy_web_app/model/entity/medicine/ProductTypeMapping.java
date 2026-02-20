package com.bush.pharmacy_web_app.model.entity.medicine;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_type_mapping")
public class ProductTypeMapping {
    @EmbeddedId
    @JsonUnwrapped
    private ProductTypeMappingId id;

    @Column(name = "is_main", nullable = false)
    private Boolean isMain;
}

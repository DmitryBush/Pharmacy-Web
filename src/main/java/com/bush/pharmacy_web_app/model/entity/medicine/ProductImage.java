package com.bush.pharmacy_web_app.model.entity.medicine;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString(exclude = "product")
@Table(name = "product_images")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class)
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id", nullable = false)
    private Long id;
    @Column(name = "image_path", nullable = false, unique = true, length = 512)
    private String path;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_key_product_id", nullable = false)
    private Product product;
}

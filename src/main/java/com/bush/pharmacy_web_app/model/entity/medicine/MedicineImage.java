package com.bush.pharmacy_web_app.model.entity.medicine;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString(exclude = "medicine")
@Table(name = "medicine_image")
public class MedicineImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id", nullable = false)
    private Long id;
    @Column(name = "image_path", nullable = false, unique = true, length = 512)
    private String path;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_key_medicine_id", nullable = false)
    private Medicine medicine;
}

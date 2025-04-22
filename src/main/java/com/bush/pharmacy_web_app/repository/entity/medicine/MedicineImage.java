package com.bush.pharmacy_web_app.repository.entity.medicine;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "medicine_image")
public class MedicineImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id", nullable = false)
    private Long id;
    @Column(name = "image_path", nullable = false, length = 512)
    private String path;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_key_medicine_id", nullable = false)
    private Medicine medicine;
}

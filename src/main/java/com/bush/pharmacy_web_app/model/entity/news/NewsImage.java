package com.bush.pharmacy_web_app.model.entity.news;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "news_image")
public class NewsImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "image_work_path", nullable = false, length = 4096)
    private String imageWorkPath;

    @ManyToOne
    @JoinColumn(name = "f_key_news_id")
    private News news;
}

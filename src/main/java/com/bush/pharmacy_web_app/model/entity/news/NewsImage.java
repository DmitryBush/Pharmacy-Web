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
    @Column(name = "image_work_path", nullable = false)
    private String imageLinkPath;

    @ManyToOne
    @JoinColumn(name = "f_key_news_id", nullable = false)
    private News news;
}

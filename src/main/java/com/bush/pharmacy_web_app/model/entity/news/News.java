package com.bush.pharmacy_web_app.model.entity.news;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 64, unique = true, updatable = false)
    private String slug;
    @Column(name = "created_time", nullable = false)
    private ZonedDateTime creationTime;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String body;

    @ManyToOne
    @JoinColumn(name = "f_key_type", nullable = false)
    private NewsType type;
    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NewsImage> newsImageList = new ArrayList<>();

    public void addImage(NewsImage image) {
        image.setNews(this);
        newsImageList.add(image);
    }

    public void removeImage(NewsImage image) {
        newsImageList.remove(image);
    }
}

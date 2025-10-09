package com.bush.pharmacy_web_app.model.entity.news;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "news_type")
public class NewsType {
    @Id
    @Column(nullable = false, length = 25)
    private String type;
}

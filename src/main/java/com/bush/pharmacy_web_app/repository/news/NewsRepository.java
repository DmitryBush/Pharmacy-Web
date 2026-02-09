package com.bush.pharmacy_web_app.repository.news;

import com.bush.pharmacy_web_app.model.entity.news.News;
import com.bush.pharmacy_web_app.repository.news.filter.FilterNewsRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long>, FilterNewsRepository {
    Optional<News> findBySlug(String slug);
}

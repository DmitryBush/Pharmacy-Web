package com.bush.pharmacy_web_app.repository.news;

import com.bush.pharmacy_web_app.model.entity.news.NewsType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsTypeRepository extends JpaRepository<NewsType, String> {
}

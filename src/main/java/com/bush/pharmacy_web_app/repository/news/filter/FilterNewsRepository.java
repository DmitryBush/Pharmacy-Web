package com.bush.pharmacy_web_app.repository.news.filter;

import com.bush.pharmacy_web_app.model.entity.news.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FilterNewsRepository {
    Page<News> findAllByFilter(NewsFilter filter, Pageable pageable);
}

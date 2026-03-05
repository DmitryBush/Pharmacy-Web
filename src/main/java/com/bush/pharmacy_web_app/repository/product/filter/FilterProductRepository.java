package com.bush.pharmacy_web_app.repository.product.filter;

import com.bush.pharmacy_web_app.model.entity.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FilterProductRepository {
    Page<Product> findAllByFilter(ProductFilter filter, Pageable pageable);
}

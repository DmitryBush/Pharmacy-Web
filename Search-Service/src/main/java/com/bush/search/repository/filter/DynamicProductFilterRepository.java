package com.bush.search.repository.filter;

import com.bush.search.domain.document.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DynamicProductFilterRepository {
    Page<Product> findProductsByFilter(com.bush.search.domain.dto.ProductFilter filter, Pageable pageable);
}

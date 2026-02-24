package com.bush.search.repository.filter;

import com.bush.search.domain.document.product.Product;
import com.bush.search.controller.response.FilteredResponse;
import com.bush.search.domain.dto.ProductFilter;
import com.bush.search.repository.FilterResultTuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DynamicProductFilterRepository {
    FilterResultTuple<Page<Product>, ProductAggregation> findProductsByFilter(ProductFilter filter, Pageable pageable);
}

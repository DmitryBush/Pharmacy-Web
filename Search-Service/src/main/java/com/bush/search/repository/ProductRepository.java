package com.bush.search.repository;

import com.bush.search.domain.document.product.Product;
import com.bush.search.repository.filter.DynamicProductFilterRepository;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ProductRepository extends ElasticsearchRepository<Product, String>, DynamicProductFilterRepository {
    List<Product> findByNameContainingIgnoreCase(String name);
}

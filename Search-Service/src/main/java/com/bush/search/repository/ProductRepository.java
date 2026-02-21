package com.bush.search.repository;

import com.bush.search.domain.document.product.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ProductRepository extends ElasticsearchRepository<Product, String> {
    List<Product> findByNameContainingIgnoreCase(String name);
}

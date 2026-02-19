package com.bush.search.repository;

import com.bush.search.domain.document.product.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductRepository extends ElasticsearchRepository<Product, String> {
}

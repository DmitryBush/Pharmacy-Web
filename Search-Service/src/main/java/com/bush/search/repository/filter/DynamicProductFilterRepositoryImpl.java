package com.bush.search.repository.filter;

import com.bush.search.domain.document.product.Product;
import com.bush.search.domain.dto.ProductFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.CriteriaQueryBuilder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class DynamicProductFilterRepositoryImpl implements DynamicProductFilterRepository {
    private final ElasticsearchOperations elasticsearchClient;
    @Override
    public Page<Product> findProductsByFilter(ProductFilter filter, Pageable pageable) {
        Criteria criteria = new Criteria("type.typeName").is(filter.type());

        if (Objects.nonNull(filter.manufacturers())) {
            criteria = criteria.and("manufacturers.name").in(filter.manufacturers());
        }
        if (Objects.nonNull(filter.countries())) {
            criteria = criteria.and("manufacturers.country.countryName").in(filter.countries());
        }
        if (Objects.nonNull(filter.activeIngredients())) {
            criteria = criteria.and("activeIngredient").in(filter.activeIngredients());
        }
        if (Objects.nonNull(filter.minPrice())) {
            criteria = criteria.and("price").greaterThanEqual(filter.minPrice());
        }
        if (Objects.nonNull(filter.maxPrice())) {
            criteria = criteria.and("price").lessThanEqual(filter.maxPrice());
        }
        if (Objects.nonNull(filter.recipe())) {
            if (filter.recipe().equals(1)) {
                criteria = criteria.and("recipe").is(true);
            } else if (filter.recipe().equals(2)) {
                criteria = criteria.and("recipe").is(false);
            }
        }
        CriteriaQuery criteriaQuery = new CriteriaQueryBuilder(criteria).withPageable(pageable).build();

        SearchHits<Product> productSearchHits = elasticsearchClient.search(criteriaQuery, Product.class);
        List<Product> filteredProducts = productSearchHits.stream()
                .map(SearchHit::getContent)
                .toList();
        return new PageImpl<>(filteredProducts, pageable, productSearchHits.getTotalHits());
    }
}

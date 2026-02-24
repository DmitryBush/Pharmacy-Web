package com.bush.search.repository.filter;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.Buckets;
import co.elastic.clients.elasticsearch._types.aggregations.MultiBucketBase;
import co.elastic.clients.elasticsearch._types.aggregations.NestedAggregate;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsAggregate;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.bush.search.domain.document.product.Product;
import com.bush.search.domain.dto.ProductFilter;
import com.bush.search.repository.FilterResultTuple;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregation;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregations;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.AggregationsContainer;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class DynamicProductFilterRepositoryImpl implements DynamicProductFilterRepository {
    private final ElasticsearchOperations elasticsearchClient;

    @Override
    public FilterResultTuple<Page<Product>, ProductAggregation> findProductsByFilter(ProductFilter filter, Pageable pageable) {
        BoolQuery boolQuery = BoolQuery.of(builder -> builder
                .filter(f -> f.nested(n -> n.path("type")
                        .query(q -> q.term(t -> t.field("type.typeName.keyword")
                                .value(filter.type())))))
                .filter(createManufacturerQuery(filter))
                .filter(createCountryQuery(filter))
                .filter(createActiveIngredientQuery(filter))
                .filter(createPriceQuery(filter))
                .filter(createRecipeQuery(filter))
        );
        NativeQuery nativeQuery = NativeQuery.builder()
                .withQuery(boolQuery._toQuery())
                .withAggregation("manufacturers", createNestedAggregation("manufacturer",
                        "manufacturer.name.keyword"))
                .withAggregation("countries", createNestedAggregation("manufacturer.country",
                        "manufacturer.country.countryName.keyword"))
                .withAggregation("activeIngredients", Aggregation.of(a -> a.terms(t ->
                        t.field("activeIngredient").size(100))))
                .build();

        SearchHits<Product> productSearchHits = elasticsearchClient.search(nativeQuery, Product.class);
        List<Product> filteredProducts = productSearchHits.stream()
                .map(SearchHit::getContent)
                .toList();
        Page<Product> productPage = new PageImpl<>(filteredProducts, pageable, productSearchHits.getTotalHits());
        return new FilterResultTuple<>(productPage,
                getProductAggregation(Objects.requireNonNull(productSearchHits.getAggregations())));
    }

    private List<Query> createPriceQuery(ProductFilter filter) {
        List<Query> queries = new ArrayList<>();
        Optional.ofNullable(filter.minPrice())
                .ifPresent(minPrice -> queries.add(
                        new Query.Builder().range(r -> r.number(n -> n.field("price")
                                .gte(minPrice.doubleValue()))).build()));
        Optional.ofNullable(filter.maxPrice())
                .ifPresent(maxPrice -> queries.add(
                        new Query.Builder().range(r -> r.number(n -> n.field("price")
                                .lte(maxPrice.doubleValue()))).build()));
        return queries;
    }

    private List<Query> createRecipeQuery(ProductFilter filter) {
        Query.Builder builder = new Query.Builder();
        List<Query> queries = new ArrayList<>();
        if (filter.recipe().equals(1)) {
            queries.add(builder.term(t -> t.field("recipe").value(true)).build());
        } else if (filter.recipe().equals(2)) {
            queries.add(builder.term(t -> t.field("recipe").value(false)).build());
        }
        return queries;
    }

    private List<Query> createManufacturerQuery(ProductFilter filter) {
        Query.Builder builder = new Query.Builder();
        List<Query> queries = new ArrayList<>();
        if (Objects.nonNull(filter.manufacturers())) {
            for (String manufacturer : filter.manufacturers()) {
                queries.add(builder.nested(n ->
                        n.path("manufacturer").query(q -> q.term(t -> t.field("manufacturer.name.keyword")
                                .value(manufacturer)))).build());
            }
        }
        return queries;
    }

    private List<Query> createCountryQuery(ProductFilter filter) {
        Query.Builder builder = new Query.Builder();
        List<Query> queries = new ArrayList<>();
        if (Objects.nonNull(filter.countries())) {
            for (String country : filter.countries()) {
                queries.add(builder.nested(n ->
                        n.path("manufacturer.country").query(q ->
                                q.term(t -> t.field("manufacturer.country.countryName.keyword")
                                        .value(country)))).build());
            }
        }
        return queries;
    }

    private List<Query> createActiveIngredientQuery(ProductFilter filter) {
        Query.Builder builder = new Query.Builder();
        List<Query> queries = new ArrayList<>();
        if (Objects.nonNull(filter.activeIngredients())) {
            for (String activeIngredient : filter.activeIngredients()) {
                queries.add(builder.term(t -> t.field("activeIngredient").value(activeIngredient)).build());
            }
        }
        return queries;
    }

    private Aggregation createNestedAggregation(String nestedObjectName, String fieldName) {
        Aggregation aggregation = Aggregation.of(a -> a.terms(t -> t.field(fieldName).size(100)));
        return Aggregation.of(a -> a.nested(n -> n.path(nestedObjectName)).aggregations(fieldName, aggregation));
    }

    private ProductAggregation getProductAggregation(AggregationsContainer<?> aggregationContainer) {
        if (aggregationContainer.getClass().isAssignableFrom(ElasticsearchAggregations.class)) {
            ElasticsearchAggregations esAggs = (ElasticsearchAggregations) aggregationContainer;

            Map<String, Long> manufacturers = getBucketNestedAggregation(esAggs.get("manufacturers"));
            Map<String, Long> countries = getBucketNestedAggregation(esAggs.get("countries"));
            Map<String, Long> activeIngredients = getBucketAggregation(esAggs.get("activeIngredients"));
            return new ProductAggregation(manufacturers, countries, activeIngredients);
        }
        throw new IllegalArgumentException("Unknown aggregation container class");
    }

    private Map<String, Long> getBucketAggregation(ElasticsearchAggregation aggregation) {
        return Optional.ofNullable(aggregation).stream()
                .map(ElasticsearchAggregation::aggregation)
                .map(org.springframework.data.elasticsearch.client.elc.Aggregation::getAggregate)
                .map(Aggregate::sterms)
                .map(StringTermsAggregate::buckets)
                .map(Buckets::array)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(b -> b.key().stringValue(), MultiBucketBase::docCount));
    }

    private Map<String, Long> getBucketNestedAggregation(ElasticsearchAggregation aggregation) {
        return Optional.ofNullable(aggregation).stream()
                .map(ElasticsearchAggregation::aggregation)
                .map(org.springframework.data.elasticsearch.client.elc.Aggregation::getAggregate)
                .map(Aggregate::nested)
                .map(NestedAggregate::aggregations)
                .map(Map::values)
                .flatMap(Collection::stream)
                .map(Aggregate::sterms)
                .map(StringTermsAggregate::buckets)
                .map(Buckets::array)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(b -> b.key().stringValue(), MultiBucketBase::docCount));
    }
}

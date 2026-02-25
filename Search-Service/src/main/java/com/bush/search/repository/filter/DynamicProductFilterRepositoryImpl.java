package com.bush.search.repository.filter;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.Buckets;
import co.elastic.clients.elasticsearch._types.aggregations.MultiBucketBase;
import co.elastic.clients.elasticsearch._types.aggregations.NestedAggregate;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsAggregate;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.util.ObjectBuilder;
import com.bush.search.domain.document.product.Product;
import com.bush.search.domain.dto.ProductFilter;
import com.bush.search.repository.FilterResultTuple;
import jakarta.annotation.Nonnull;
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

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class DynamicProductFilterRepositoryImpl implements DynamicProductFilterRepository {
    private final ElasticsearchOperations elasticsearchClient;

    @Override
    public FilterResultTuple<Page<Product>, ProductAggregation> findProductsByFilter(ProductFilter filter, Pageable pageable) {
        BoolQuery boolQuery = getBoolQuery(filter);
        NativeQuery nativeQuery = buildNativeQuery(boolQuery);

        SearchHits<Product> productSearchHits = elasticsearchClient.search(nativeQuery, Product.class);
        return new FilterResultTuple<>(buildPage(productSearchHits, pageable),
                getProductAggregation(Objects.requireNonNull(productSearchHits.getAggregations())));
    }

    private Page<Product> buildPage(SearchHits<Product> productSearchHits, Pageable pageable) {
        List<Product> filteredProducts = productSearchHits.stream().map(SearchHit::getContent).toList();
        return new PageImpl<>(filteredProducts, pageable, productSearchHits.getTotalHits());
    }

    @Nonnull
    private NativeQuery buildNativeQuery(BoolQuery boolQuery) {
        return NativeQuery.builder()
                .withQuery(boolQuery._toQuery())
                .withAggregation("manufacturers", createNestedAggregation("manufacturer",
                        "manufacturer.name.keyword"))
                .withAggregation("countries", createNestedAggregation("manufacturer.country",
                        "manufacturer.country.countryName.keyword"))
                .withAggregation("activeIngredients", Aggregation.of(a -> a.terms(t ->
                        t.field("activeIngredient").size(100))))
                .build();
    }

    private Aggregation createNestedAggregation(String nestedObjectName, String fieldName) {
        Aggregation aggregation = Aggregation.of(a -> a.terms(t -> t.field(fieldName).size(100)));
        return Aggregation.of(a -> a.nested(n -> n.path(nestedObjectName)).aggregations(fieldName, aggregation));
    }

    private BoolQuery getBoolQuery(ProductFilter filter) {
        return BoolQuery.of(builder -> builder
                .filter(f -> f.nested(n -> n.path("type").query(q ->
                        q.term(t -> t.field("type.typeName.keyword").value(filter.type())))))
                .filter(createNestedFilterCriteriaQuery(filter.manufacturers(), "manufacturer",
                        "manufacturer.name.keyword"))
                .filter(createNestedFilterCriteriaQuery(filter.countries(), "manufacturer.country",
                        "manufacturer.country.countryName.keyword"))
                .filter(createFilterCriteriaQuery(filter.activeIngredients(), "activeIngredient"))
                .filter(createPriceQuery(filter))
                .filter(createRecipeQuery(filter))
        );
    }

    private List<Query> createPriceQuery(ProductFilter filter) {
        return Stream.of(
                        Optional.ofNullable(filter.minPrice())
                                .map(minPrice -> new Query.Builder()
                                        .range(r -> r.number(n -> n.field("price")
                                                .gte(minPrice.doubleValue())))).orElse(null),
                        Optional.ofNullable(filter.maxPrice())
                                .map(maxPrice -> new Query.Builder()
                                        .range(r -> r.number(n -> n.field("price")
                                                .lte(maxPrice.doubleValue())))).orElse(null)
                )
                .filter(Objects::nonNull)
                .map(ObjectBuilder::build)
                .toList();
    }

    private List<Query> createRecipeQuery(ProductFilter filter) {
        return Stream.of(buildRecipeQuery(filter.recipe())).filter(Objects::nonNull).toList();
    }

    private Query buildRecipeQuery(Integer recipe) {
        Query.Builder builder = new Query.Builder();
        if (recipe.equals(1)) {
            return builder.term(t -> t.field("recipe").value(true)).build();
        } else if (recipe.equals(2)) {
            return builder.term(t -> t.field("recipe").value(false)).build();
        }
        return null;
    }

    private List<Query> createNestedFilterCriteriaQuery(List<String> filteringObjects, String path, String field) {
        Query.Builder builder = new Query.Builder();
        return Optional.ofNullable(filteringObjects).stream()
                .flatMap(Collection::stream)
                .map(filterCriteria -> builder.nested(n -> n.path(path)
                        .query(q -> q.term(t -> t.field(field).value(filterCriteria)))))
                .map(ObjectBuilder::build)
                .toList();
    }

    private List<Query> createFilterCriteriaQuery(List<String> filteringObjects, String field) {
        Query.Builder builder = new Query.Builder();
        return Optional.ofNullable(filteringObjects).stream()
                .flatMap(Collection::stream)
                .map(filterCriteria -> builder.term(t -> t.field(field).value(filterCriteria)))
                .map(ObjectBuilder::build)
                .toList();
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
                .map(Aggregate::sterms).map(StringTermsAggregate::buckets)
                .map(Buckets::array).flatMap(Collection::stream)
                .collect(Collectors.toMap(b -> b.key().stringValue(), MultiBucketBase::docCount));
    }

    private Map<String, Long> getBucketNestedAggregation(ElasticsearchAggregation aggregation) {
        return Optional.ofNullable(aggregation).stream()
                .map(ElasticsearchAggregation::aggregation)
                .map(org.springframework.data.elasticsearch.client.elc.Aggregation::getAggregate)
                .map(Aggregate::nested).map(NestedAggregate::aggregations).map(Map::values)
                .flatMap(Collection::stream).map(Aggregate::sterms)
                .map(StringTermsAggregate::buckets)
                .map(Buckets::array)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(b -> b.key().stringValue(), MultiBucketBase::docCount));
    }
}

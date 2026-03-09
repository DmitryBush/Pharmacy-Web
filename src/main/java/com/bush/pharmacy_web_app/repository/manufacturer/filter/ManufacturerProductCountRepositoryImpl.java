package com.bush.pharmacy_web_app.repository.manufacturer.filter;

import com.bush.pharmacy_web_app.model.dto.manufacturer.ManufacturerCountProductFilterResponse;
import com.bush.pharmacy_web_app.model.entity.manufacturer.Manufacturer;
import com.bush.pharmacy_web_app.model.entity.product.Product;
import com.bush.pharmacy_web_app.repository.product.filter.ProductFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.bush.pharmacy_web_app.repository.product.filter.FilterProductRepositoryImpl.createActiveIngredientPredicates;
import static com.bush.pharmacy_web_app.repository.product.filter.FilterProductRepositoryImpl.createCountryPredicates;
import static com.bush.pharmacy_web_app.repository.product.filter.FilterProductRepositoryImpl.createManufacturerPredicates;
import static com.bush.pharmacy_web_app.repository.product.filter.FilterProductRepositoryImpl.createMaxPricePredicate;
import static com.bush.pharmacy_web_app.repository.product.filter.FilterProductRepositoryImpl.createRecipePredicate;
import static com.bush.pharmacy_web_app.repository.product.filter.FilterProductRepositoryImpl.createTypePredicate;

@RequiredArgsConstructor
public class ManufacturerProductCountRepositoryImpl implements ManufacturerProductCountRepository {
    private final EntityManager entityManager;
    @Override
    public List<ManufacturerCountProductFilterResponse> findAllManufacturersByProductFilter(ProductFilter filter) {
        var cb = entityManager.getCriteriaBuilder();
        return filter(createFilteredQuery(filter, cb));
    }

    private CriteriaQuery<ManufacturerCountProductFilterResponse> createFilteredQuery(ProductFilter filter, CriteriaBuilder cb) {
        var query = cb.createQuery(ManufacturerCountProductFilterResponse.class);
        var root = query.from(Manufacturer.class);

        Expression<String> manufacturerName = root.get("name");
        Expression<Long> queryManufacturerCount = cb.count(cb.literal(1));
        query.select(cb.construct(ManufacturerCountProductFilterResponse.class, manufacturerName, queryManufacturerCount));

        query.groupBy(root.get("name"));

        var predicates = buildPredicates(filter, cb, root);
        return query.where(predicates.toArray(Predicate[]::new));
    }

    private List<Predicate> buildPredicates(ProductFilter filter, CriteriaBuilder cb, Root<Manufacturer> root) {
        Join<Manufacturer, Product> productJoin = root.join("products");
        List<Predicate> predicates = new ArrayList<>();

        createMaxPricePredicate(filter, cb, productJoin)
                .ifPresent(predicates::add);
        createMaxPricePredicate(filter, cb, productJoin)
                .ifPresent(predicates::add);

        createTypePredicate(filter, cb, productJoin.join("type"))
                .ifPresent(predicates::add);
        createRecipePredicate(filter, cb, productJoin)
                .ifPresent(predicates::add);

        createManufacturerPredicates(filter, cb, root, predicates);
        createCountryPredicates(filter, cb, root.join("country"), predicates);
        createActiveIngredientPredicates(filter, cb, productJoin, predicates);
        return predicates;
    }

    private List<ManufacturerCountProductFilterResponse> filter(CriteriaQuery<ManufacturerCountProductFilterResponse> manufacturerCriteriaQuery) {
        var typed = entityManager.createQuery(manufacturerCriteriaQuery);

        return typed.getResultList();
    }
}

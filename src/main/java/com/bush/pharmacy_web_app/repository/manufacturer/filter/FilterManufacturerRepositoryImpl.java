package com.bush.pharmacy_web_app.repository.manufacturer.filter;

import com.bush.pharmacy_web_app.model.entity.manufacturer.Manufacturer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class FilterManufacturerRepositoryImpl implements FilterManufacturerRepository{
    private final EntityManager entityManager;
    @Override
    public List<Manufacturer> findAllByFilter(ManufacturerFilter filter) {
        var cb = entityManager.getCriteriaBuilder();
        return filter(createFilteredQuery(filter, cb));
    }

    private CriteriaQuery<Manufacturer> createFilteredQuery(ManufacturerFilter filter, CriteriaBuilder cb) {
        var query = cb.createQuery(Manufacturer.class);
        var root = query.from(Manufacturer.class);
        query.select(root);

        var predicates = buildPredicates(filter, cb, root);
        return query.where(predicates.toArray(Predicate[]::new));
    }

    private List<Predicate> buildPredicates(ManufacturerFilter filter, CriteriaBuilder cb, Root<Manufacturer> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (Optional.ofNullable(filter.name()).isPresent()) {
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + filter.name().toLowerCase() + "%"));
        }
        return predicates;
    }

    private List<Manufacturer> filter(CriteriaQuery<Manufacturer> manufacturerCriteriaQuery) {
        var typed = entityManager.createQuery(manufacturerCriteriaQuery);

        return typed.getResultList();
    }
}

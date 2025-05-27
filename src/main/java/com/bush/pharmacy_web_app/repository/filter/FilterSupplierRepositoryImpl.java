package com.bush.pharmacy_web_app.repository.filter;

import com.bush.pharmacy_web_app.repository.entity.Supplier;
import com.bush.pharmacy_web_app.repository.entity.medicine.Medicine;
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
public class FilterSupplierRepositoryImpl implements FilterSupplierRepository {
    private final EntityManager entityManager;
    @Override
    public List<Supplier> findAllByFilter(SupplierFilter filter) {
        var cb = entityManager.getCriteriaBuilder();

        return applyFilters(createFilteredQuery(filter, cb));
    }

    private CriteriaQuery<Supplier> createFilteredQuery(SupplierFilter filter, CriteriaBuilder cb) {
        var query = cb.createQuery(Supplier.class);
        var supplier = query.from(Supplier.class);
        query.select(supplier);

        var predicates = buildPredicates(filter, cb, supplier);
        return query.where(predicates.toArray(Predicate[]::new));
    }

    private List<Predicate> buildPredicates(SupplierFilter filter, CriteriaBuilder criteriaBuilder,
                                            Root<Supplier> supplier) {
        List<Predicate> predicates = new ArrayList<>();

        if (Optional.ofNullable(filter.name()).isPresent()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(supplier.get("name")),
                    "%" + filter.name().toLowerCase() + "%"));
        }
        return predicates;
    }

    private List<Supplier> applyFilters(CriteriaQuery<Supplier> query) {
        var typedQuery = entityManager.createQuery(query);

        return typedQuery.getResultList();
    }
}

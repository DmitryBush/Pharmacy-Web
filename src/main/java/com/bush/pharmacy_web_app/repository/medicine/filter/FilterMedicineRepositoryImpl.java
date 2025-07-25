package com.bush.pharmacy_web_app.repository.medicine.filter;

import com.bush.pharmacy_web_app.model.entity.medicine.Medicine;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class FilterMedicineRepositoryImpl implements FilterMedicineRepository {
    private final EntityManager entityManager;
    @Override
    public Page<Medicine> findAllByFilter(MedicineFilter filter, Pageable pageable) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();

        var medicines = applyPagination(createFilteredQuery(filter, criteriaBuilder, pageable), pageable);

        Long totalElementsCount = entityManager.createQuery(createCountQuery(filter, criteriaBuilder))
                .getSingleResult();
        return new PageImpl<>(medicines, pageable, totalElementsCount);
    }

    private CriteriaQuery<Medicine> createFilteredQuery(MedicineFilter filter, CriteriaBuilder criteriaBuilder,
                                                        Pageable pageable) {
        var query = criteriaBuilder.createQuery(Medicine.class);
        var medicine = query.from(Medicine.class);
        query.select(medicine);

        var predicates = buildPredicates(filter, criteriaBuilder, medicine);
        query.where(predicates.toArray(Predicate[]::new));
        query.orderBy(QueryUtils.toOrders(pageable.getSort(), medicine, criteriaBuilder));

        return query;
    }

    private CriteriaQuery<Long> createCountQuery(MedicineFilter filter, CriteriaBuilder criteriaBuilder) {
        var query = criteriaBuilder.createQuery(Long.class);
        var medicine = query.from(Medicine.class);
        query.select(criteriaBuilder.count(medicine));

        var predicates = buildPredicates(filter, criteriaBuilder, medicine);
        query.where(predicates.toArray(Predicate[]::new));

        return query;
    }

    private static List<Predicate> buildPredicates(MedicineFilter filter, CriteriaBuilder criteriaBuilder,
                                                   Root<Medicine> medicine) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter.type() != null) {
            var typeJoin = medicine.join("type");
            List<Predicate> typesPredicates = new ArrayList<>();
            for (var type : filter.type()) {
                typesPredicates.add(criteriaBuilder.like(typeJoin.get("type"), type));
            }
            predicates.add(criteriaBuilder.or(typesPredicates.toArray(Predicate[]::new)));
        }
        if (filter.manufacturer() != null) {
            List<Predicate> manufacturerPredicates = new ArrayList<>();
            var manufacturerJoin = medicine.join("manufacturer");
            for (var manufacturer : filter.manufacturer()) {
                manufacturerPredicates.add(criteriaBuilder.like(manufacturerJoin.get("name"), manufacturer));
            }
            predicates.add(criteriaBuilder.or(manufacturerPredicates.toArray(Predicate[]::new)));
        }
        if (filter.recipe() != null) {
            if (filter.recipe().equals(1))
                predicates.add(criteriaBuilder.equal(medicine.get("recipe"), true));
            else if (filter.recipe().equals(2))
                predicates.add(criteriaBuilder.equal(medicine.get("recipe"), false));
        }
        if (filter.minPrice() != null) {
            predicates.add(criteriaBuilder.ge(medicine.get("price"), filter.minPrice()));
        }
        if (filter.maxPrice() != null) {
            predicates.add(criteriaBuilder.le(medicine.get("price"), filter.maxPrice()));
        }
        return predicates;
    }

    private List<Medicine> applyPagination(CriteriaQuery<Medicine> query, Pageable pageable) {
        var typedQuery = entityManager.createQuery(query);

        typedQuery.setFirstResult(Math.toIntExact(pageable.getOffset()));
        typedQuery.setMaxResults(typedQuery.getMaxResults());

        return typedQuery.getResultList();
    }
}

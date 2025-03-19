package com.bush.pharmacy_web_app.repository.filter;

import com.bush.pharmacy_web_app.repository.entity.Medicine;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class FilterMedicineRepositoryImpl implements FilterMedicineRepository {
    private final EntityManager entityManager;
    @Override
    public List<Medicine> findAllByFilter(MedicineFilter filter) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var query = criteriaBuilder.createQuery(Medicine.class);

        var medicine = query.from(Medicine.class);
        query.select(medicine);

        List<Predicate> predicates = new ArrayList<>();

        if (filter.type() != null) {
            List<Predicate> typesPredicates = new ArrayList<>();
            for (var type : filter.type()) {
                typesPredicates.add(criteriaBuilder.like(medicine.get("type"), type));
            }
            predicates.add(criteriaBuilder.or(typesPredicates.toArray(Predicate[]::new)));
        }
        if (filter.manufacturer() != null) {
            List<Predicate> manufacturerPredicates = new ArrayList<>();
            for (var manufacturer : filter.manufacturer()) {
                manufacturerPredicates.add(criteriaBuilder.like(medicine.get("manufacturer"), manufacturer));
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
        query.where(predicates.toArray(Predicate[]::new));

        return entityManager.createQuery(query).getResultList();
    }
}

package com.bush.pharmacy_web_app.repository.manufacturer.filter;

import com.bush.pharmacy_web_app.model.dto.manufacturer.ManufacturerCountProductFilterResponse;
import com.bush.pharmacy_web_app.model.entity.manufacturer.Manufacturer;
import com.bush.pharmacy_web_app.model.entity.medicine.MedicineType;
import com.bush.pharmacy_web_app.model.entity.medicine.Product;
import com.bush.pharmacy_web_app.model.entity.medicine.ProductTypeMapping;
import com.bush.pharmacy_web_app.repository.medicine.filter.FilterMedicineRepositoryImpl;
import com.bush.pharmacy_web_app.repository.medicine.filter.MedicineFilter;
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
import java.util.Optional;

import static com.bush.pharmacy_web_app.repository.medicine.filter.FilterMedicineRepositoryImpl.createActiveIngredientPredicates;
import static com.bush.pharmacy_web_app.repository.medicine.filter.FilterMedicineRepositoryImpl.createCountryPredicates;
import static com.bush.pharmacy_web_app.repository.medicine.filter.FilterMedicineRepositoryImpl.createManufacturerPredicates;
import static com.bush.pharmacy_web_app.repository.medicine.filter.FilterMedicineRepositoryImpl.createMaxPricePredicate;
import static com.bush.pharmacy_web_app.repository.medicine.filter.FilterMedicineRepositoryImpl.createRecipePredicate;
import static com.bush.pharmacy_web_app.repository.medicine.filter.FilterMedicineRepositoryImpl.createTypePredicate;

@RequiredArgsConstructor
public class ManufacturerProductCountRepositoryImpl implements ManufacturerProductCountRepository {
    private final EntityManager entityManager;
    @Override
    public List<ManufacturerCountProductFilterResponse> findAllManufacturersByProductFilter(MedicineFilter filter) {
        var cb = entityManager.getCriteriaBuilder();
        return filter(createFilteredQuery(filter, cb));
    }

    private CriteriaQuery<ManufacturerCountProductFilterResponse> createFilteredQuery(MedicineFilter filter, CriteriaBuilder cb) {
        var query = cb.createQuery(ManufacturerCountProductFilterResponse.class);
        var root = query.from(Manufacturer.class);

        Expression<String> manufacturerName = root.get("name");
        Expression<Long> queryManufacturerCount = cb.count(cb.literal(1));
        query.select(cb.construct(ManufacturerCountProductFilterResponse.class, manufacturerName, queryManufacturerCount));

        query.groupBy(root.get("name"));

        var predicates = buildPredicates(filter, cb, root);
        return query.where(predicates.toArray(Predicate[]::new));
    }

    private List<Predicate> buildPredicates(MedicineFilter filter, CriteriaBuilder cb, Root<Manufacturer> root) {
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

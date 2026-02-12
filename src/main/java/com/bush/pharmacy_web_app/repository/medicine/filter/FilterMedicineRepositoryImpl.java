package com.bush.pharmacy_web_app.repository.medicine.filter;

import com.bush.pharmacy_web_app.model.entity.manufacturer.Country;
import com.bush.pharmacy_web_app.model.entity.manufacturer.Manufacturer;
import com.bush.pharmacy_web_app.model.entity.medicine.Product;
import com.bush.pharmacy_web_app.model.entity.medicine.ProductTypeMapping;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class FilterMedicineRepositoryImpl implements FilterMedicineRepository {
    private final EntityManager entityManager;

    private static List<Predicate> buildPredicates(MedicineFilter filter, CriteriaBuilder cb, Root<Product> root) {
        List<Predicate> predicates = new ArrayList<>();

        createMinPricePredicate(filter, cb, root)
                .ifPresent(predicates::add);
        createMaxPricePredicate(filter, cb, root)
                .ifPresent(predicates::add);

        createTypePredicate(filter, cb, root.join("type"))
                .ifPresent(predicates::add);
        createRecipePredicate(filter, cb, root)
                .ifPresent(predicates::add);

        createManufacturerPredicates(filter, cb, root.join("manufacturer"), predicates);
        createCountryPredicates(filter, cb, root.join("manufacturer").join("country"), predicates);
        createActiveIngredientPredicates(filter, cb, root, predicates);
        return predicates;
    }

    public static <T> Optional<Predicate> createRecipePredicate(MedicineFilter filter, CriteriaBuilder cb,
                                                                From<T, Product> root) {
        return Optional.ofNullable(filter.recipe())
                .map(recipe -> {
                    if (recipe.equals(1)) {
                        return cb.equal(root.get("recipe"), true);
                    } else if (recipe.equals(2)) {
                        return cb.equal(root.get("recipe"), false);
                    }
                    return null;
                });
    }

    public static <T> Optional<Predicate> createMaxPricePredicate(MedicineFilter filter, CriteriaBuilder cb,
                                                                  From<T, Product> medicine) {
        return Optional.ofNullable(filter.maxPrice())
                .map(maxPrice -> cb.le(medicine.get("price"), maxPrice));
    }

    public static <T> Optional<Predicate> createMinPricePredicate(MedicineFilter filter, CriteriaBuilder cb,
                                                                  From<T, Product> productRoot) {
        return Optional.ofNullable(filter.minPrice())
                .map(minPrice -> cb.ge(productRoot.get("price"), minPrice));
    }

    public static <S> Optional<Predicate> createTypePredicate(MedicineFilter filter, CriteriaBuilder cb,
                                                              From<S, ProductTypeMapping> productRoot) {
        return Optional.ofNullable(filter.type())
                .map(type -> cb.equal(productRoot.get("id").get("type").get("type"), type));
    }

    public static <T> void createActiveIngredientPredicates(MedicineFilter filter, CriteriaBuilder cb,
                                                            From<T, Product> medicine, List<Predicate> predicates) {
        List<Predicate> activeIngredientPredicates = new ArrayList<>();
        if (Objects.nonNull(filter.activeIngredients())) {
            for (String activeIngredient : filter.activeIngredients()) {
                activeIngredientPredicates.add(cb.equal(medicine.get("activeIngredient"), activeIngredient));
            }
            predicates.add(cb.or(activeIngredientPredicates.toArray(Predicate[]::new)));
        }
    }

    public static <S> void createManufacturerPredicates(MedicineFilter filter, CriteriaBuilder cb,
                                                        From<S, Manufacturer> manufacturerFrom, List<Predicate> predicates) {
        List<Predicate> manufacturerPredicates = new ArrayList<>();
        if (filter.manufacturer() != null) {
            for (var manufacturer : filter.manufacturer()) {
                manufacturerPredicates.add(cb.equal(manufacturerFrom.get("name"), manufacturer));
            }
            predicates.add(cb.or(manufacturerPredicates.toArray(Predicate[]::new)));
        }
    }

    public static <S> void createCountryPredicates(MedicineFilter filter, CriteriaBuilder cb,
                                                   From<S, Country> countryFrom, List<Predicate> predicates) {
        List<Predicate> countryPredicates = new ArrayList<>();
        if (Objects.nonNull(filter.countries())) {
            for (String country : filter.countries()) {
                countryPredicates.add(cb.equal(countryFrom.get("country"), country));
            }
            predicates.add(cb.or(countryPredicates.toArray(Predicate[]::new)));
        }
    }

    @Override
    public Page<Product> findAllByFilter(MedicineFilter filter, Pageable pageable) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();

        var medicines = applyPagination(createFilteredQuery(filter, criteriaBuilder, pageable), pageable);

        Long totalElementsCount = entityManager.createQuery(createCountQuery(filter, criteriaBuilder))
                .getSingleResult();
        return new PageImpl<>(medicines, pageable, totalElementsCount);
    }

    private CriteriaQuery<Product> createFilteredQuery(MedicineFilter filter, CriteriaBuilder criteriaBuilder,
                                                       Pageable pageable) {
        var query = criteriaBuilder.createQuery(Product.class);
        var medicine = query.from(Product.class);
        query.select(medicine);

        var predicates = buildPredicates(filter, criteriaBuilder, medicine);
        query.where(predicates.toArray(Predicate[]::new));
        query.orderBy(QueryUtils.toOrders(pageable.getSort(), medicine, criteriaBuilder));

        return query;
    }

    private CriteriaQuery<Long> createCountQuery(MedicineFilter filter, CriteriaBuilder criteriaBuilder) {
        var query = criteriaBuilder.createQuery(Long.class);
        var medicine = query.from(Product.class);
        query.select(criteriaBuilder.count(medicine));

        var predicates = buildPredicates(filter, criteriaBuilder, medicine);
        query.where(predicates.toArray(Predicate[]::new));

        return query;
    }

    private List<Product> applyPagination(CriteriaQuery<Product> query, Pageable pageable) {
        var typedQuery = entityManager.createQuery(query);

        typedQuery.setFirstResult(Math.toIntExact(pageable.getOffset()));
        typedQuery.setMaxResults(typedQuery.getMaxResults());

        return typedQuery.getResultList();
    }
}

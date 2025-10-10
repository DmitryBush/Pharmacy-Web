package com.bush.pharmacy_web_app.repository.news.filter;

import com.bush.pharmacy_web_app.model.entity.news.News;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class FilterNewsRepositoryImpl implements FilterNewsRepository {
    private final EntityManager entityManager;
    @Value("${time.timezone}")
    private String timezone;
    @Override
    public Page<News> findAllByFilter(NewsFilter filter, Pageable pageable) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();

        var medicines = applyPagination(createFilteredQuery(filter, criteriaBuilder, pageable), pageable);

        Long totalElementsCount = entityManager.createQuery(createCountQuery(filter, criteriaBuilder))
                .getSingleResult();
        return new PageImpl<>(medicines, pageable, totalElementsCount);
    }

    private CriteriaQuery<News> createFilteredQuery(NewsFilter filter, CriteriaBuilder criteriaBuilder,
                                                        Pageable pageable) {
        var query = criteriaBuilder.createQuery(News.class);
        var news = query.from(News.class);
        query.select(news);

        var predicates = buildPredicates(filter, criteriaBuilder, news);
        query.where(predicates.toArray(Predicate[]::new));
        query.orderBy(QueryUtils.toOrders(pageable.getSort(), news, criteriaBuilder));

        return query;
    }

    private CriteriaQuery<Long> createCountQuery(NewsFilter filter, CriteriaBuilder criteriaBuilder) {
        var query = criteriaBuilder.createQuery(Long.class);
        var news = query.from(News.class);
        query.select(criteriaBuilder.count(news));

        var predicates = buildPredicates(filter, criteriaBuilder, news);
        query.where(predicates.toArray(Predicate[]::new));

        return query;
    }

    private List<Predicate> buildPredicates(NewsFilter filter, CriteriaBuilder criteriaBuilder,
                                                   Root<News> news) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter.type() != null) {
            var typeJoin = news.join("type");
            predicates.add(criteriaBuilder.equal(typeJoin.get("type"), filter.type()));
        }
        if (filter.title() != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(news.get("title")), "%" + filter.title()
                    .toLowerCase() + "%"));
        } if (filter.creationDay() != null) {
            var startOfDay = filter.creationDay().atStartOfDay(ZoneId.of(timezone));
            var endOfDay = filter.creationDay().plusDays(1).atStartOfDay(ZoneId.of(timezone));
            predicates.add(criteriaBuilder.between(news.get("created_time"), startOfDay, endOfDay));
        }
        return predicates;
    }

    private List<News> applyPagination(CriteriaQuery<News> query, Pageable pageable) {
        var typedQuery = entityManager.createQuery(query);

        typedQuery.setFirstResult(Math.toIntExact(pageable.getOffset()));
        typedQuery.setMaxResults(typedQuery.getMaxResults());

        return typedQuery.getResultList();
    }
}

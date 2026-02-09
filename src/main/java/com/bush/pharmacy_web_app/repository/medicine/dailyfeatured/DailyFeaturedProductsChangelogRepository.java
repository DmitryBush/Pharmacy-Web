package com.bush.pharmacy_web_app.repository.medicine.dailyfeatured;

import com.bush.pharmacy_web_app.model.entity.medicine.dailyfeatured.DailyFeaturedProductsChangelog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DailyFeaturedProductsChangelogRepository extends JpaRepository<DailyFeaturedProductsChangelog, Long> {
    @Query("select d from DailyFeaturedProductsChangelog d order by d.id desc limit 1")
    Optional<DailyFeaturedProductsChangelog> findFirstOrderedByDesc();
}

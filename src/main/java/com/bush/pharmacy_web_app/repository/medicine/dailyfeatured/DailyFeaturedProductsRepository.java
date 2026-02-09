package com.bush.pharmacy_web_app.repository.medicine.dailyfeatured;

import com.bush.pharmacy_web_app.model.entity.medicine.dailyfeatured.DailyFeaturedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyFeaturedProductsRepository extends JpaRepository<DailyFeaturedProduct, Short> {
}

package com.bush.pharmacy_web_app.repository.product.dailyfeatured;

import com.bush.pharmacy_web_app.model.entity.product.dailyfeatured.DailyFeaturedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyFeaturedProductsRepository extends JpaRepository<DailyFeaturedProduct, Short> {
}

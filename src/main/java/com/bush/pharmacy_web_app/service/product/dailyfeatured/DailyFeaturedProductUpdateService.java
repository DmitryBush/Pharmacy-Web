package com.bush.pharmacy_web_app.service.product.dailyfeatured;

import com.bush.pharmacy_web_app.model.entity.product.dailyfeatured.DailyFeaturedProductsChangelog;
import com.bush.pharmacy_web_app.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DailyFeaturedProductUpdateService {
    private final Logger logger = LoggerFactory.getLogger(DailyFeaturedProductUpdateService.class);
    private final DailyFeaturedProductService dailyProductService;
    private final DailyFeaturedProductChangelogService changelogService;
    private final ProductService productService;

    @Value("${time.timezone}")
    private String timezone;
    @Value("${featured-products.count}")
    private Integer dailyFeaturedProductCount;
    private LocalDate cachedUpdateDate = null;

    public void refreshDailyProducts(Boolean forceRefresh) {
        Optional.ofNullable(cachedUpdateDate)
                .ifPresentOrElse(date -> checkAndUpdateDailyProducts(date, forceRefresh),
                        () -> changelogService.findLatestUpdateRecord()
                                .map(DailyFeaturedProductsChangelog::getCreatedAt)
                                .map(ZonedDateTime::toLocalDate)
                                .ifPresentOrElse(date -> checkAndUpdateDailyProducts(date, forceRefresh),
                                        this::updateDailyProducts));
    }

    private void checkAndUpdateDailyProducts(LocalDate updateDate, Boolean forceRefresh) {
        if (updateDate.isBefore(ZonedDateTime.now(ZoneId.of(timezone)).toLocalDate()) || forceRefresh) {
            updateDailyProducts();
            cachedUpdateDate = ZonedDateTime.now(ZoneId.of(timezone)).toLocalDate();
        } else {
            logger.info("Daily products is up-to-date");
        }
    }

    private void updateDailyProducts() {
        try {
            var randomProducts = productService.findRandomMedicine(dailyFeaturedProductCount);

            dailyProductService.createDailyFeaturedProducts(randomProducts);
            logger.info("Updating daily products completed successfully");
        } catch (Exception e) {
            logger.error("An error occurred while updating daily product: ", e);
            throw new RuntimeException(e);
        }
    }
}

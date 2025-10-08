package com.bush.pharmacy_web_app.service.medicine.dailyfeatured;

import com.bush.pharmacy_web_app.service.medicine.MedicineService;
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
    @Value("${time.timezone}")
    private String timezone;
    @Value("${featured-products.count}")
    private Integer dailyFeaturedProductCount;

    private final Logger logger = LoggerFactory.getLogger(DailyFeaturedProductUpdateService.class);

    private LocalDate cachedUpdateDate = null;

    private final DailyFeaturedProductService productService;
    private final DailyFeaturedProductChangelogService changelogService;
    private final MedicineService medicineService;

    public void refreshDailyProducts() {
        Optional.ofNullable(cachedUpdateDate)
                .ifPresentOrElse(this::checkAndUpdateDailyProducts,
                        () -> changelogService.findLatestUpdateRecord()
                                .map(record -> record.getCreatedAt().toLocalDate())
                                .ifPresentOrElse(this::checkAndUpdateDailyProducts, this::updateDailyProducts));
    }

    private void checkAndUpdateDailyProducts(LocalDate updateDate) {
        if (updateDate.isBefore(ZonedDateTime.now(ZoneId.of(timezone)).toLocalDate())) {
            updateDailyProducts();
            cachedUpdateDate = ZonedDateTime.now(ZoneId.of(timezone)).toLocalDate();
        } else {
            logger.info("Daily products is up-to-date");
        }
    }

    private void updateDailyProducts() {
        try {
            var randomProducts = medicineService.findRandomMedicine(dailyFeaturedProductCount);

            productService.createDailyFeaturedProducts(randomProducts);
            logger.info("Updating daily products completed successfully");
        } catch (Exception e) {
            logger.error("An error occurred while updating daily product: ", e);
            throw new RuntimeException(e);
        }
    }
}

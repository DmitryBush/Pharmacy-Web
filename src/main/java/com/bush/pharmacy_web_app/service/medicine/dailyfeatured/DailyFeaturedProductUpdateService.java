package com.bush.pharmacy_web_app.service.medicine.dailyfeatured;

import com.bush.pharmacy_web_app.service.medicine.MedicineService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class DailyFeaturedProductUpdateService {
    private final Logger logger = LoggerFactory.getLogger(DailyFeaturedProductUpdateService.class);

    private final DailyFeaturedProductService productService;
    private final DailyFeaturedProductChangelogService changelogService;
    private final MedicineService medicineService;

    @EventListener(ApplicationReadyEvent.class)
    private void updateDailyProductsAfterBoot() {
        changelogService.findLatestRecord()
                .map(record -> record.getCreatedAt().toLocalDate())
                .ifPresentOrElse(latestUpdateDate -> {
                    if (latestUpdateDate.isBefore(ZonedDateTime.now().toLocalDate())) {
                        updateDailyProducts();
                    }
                    logger.info("Daily products is up-to-date");
                }, this::updateDailyProducts);
    }

    @Scheduled(cron = "* 30 0 * * *", zone = "Europe/Moscow")
    private void scheduledUpdateDailyProducts() {
        updateDailyProducts();
    }

    private void updateDailyProducts() {
        try {
            var randomProducts = medicineService.findRandomMedicine(5);

            productService.deleteAllFeaturedProducts();
            productService.createDayFeaturedProducts(randomProducts);
            logger.info("Updating daily products completed successfully");
        } catch (Exception e) {
            logger.error("An error occurred while updating daily product $s", e.getCause());
            throw new RuntimeException(e);
        }
    }
}

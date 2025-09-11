package com.bush.pharmacy_web_app.service.medicine.dailyfeatured;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DailyFeaturedProductSchedulerService {
    private final DailyFeaturedProductUpdateService updateService;

    @EventListener(ApplicationReadyEvent.class)
    protected void updateDailyProductsAfterBoot() {
        updateService.refreshDailyProducts();
    }

    @Scheduled(cron = "${daily-featured-products.update-cron}", zone = "${time.timezone}")
    protected void scheduledUpdateDailyProducts() {
        updateService.refreshDailyProducts();
    }
}

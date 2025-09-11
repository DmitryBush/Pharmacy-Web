package com.bush.pharmacy_web_app.service.medicine.dailyfeatured;

import com.bush.pharmacy_web_app.model.entity.medicine.dailyfeatured.DailyFeaturedProductsChangelog;
import com.bush.pharmacy_web_app.repository.medicine.dailyfeatured.DailyFeaturedProductsChangelogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DailyFeaturedProductChangelogService {
    @Value("${time.timezone}")
    private String timezone;

    private final DailyFeaturedProductsChangelogRepository changelogRepository;

    @Transactional
    public void createLog() {
        var record = DailyFeaturedProductsChangelog.builder()
                .createdAt(ZonedDateTime.now(ZoneId.of(timezone)))
                .build();
        changelogRepository.save(record);
    }

    public Optional<DailyFeaturedProductsChangelog> findLatestUpdateRecord() {
        return changelogRepository.findFirstOrderedByDesc();
    }
}

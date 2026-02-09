package com.bush.pharmacy_web_app.repository.news.filter;

import java.time.LocalDate;

public record NewsFilter(LocalDate creationDay,
                         String title,
                         String type) {
}

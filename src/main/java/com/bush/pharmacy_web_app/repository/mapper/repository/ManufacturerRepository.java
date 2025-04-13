package com.bush.pharmacy_web_app.repository.mapper.repository;

import com.bush.pharmacy_web_app.repository.entity.manufacturer.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
}

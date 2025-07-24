package com.bush.pharmacy_web_app.repository.mapper.repository;

import com.bush.pharmacy_web_app.repository.entity.manufacturer.Manufacturer;
import com.bush.pharmacy_web_app.repository.filter.FilterManufacturerRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long>, FilterManufacturerRepository {
    List<Manufacturer> findByNameContainingIgnoreCase(String name);
}

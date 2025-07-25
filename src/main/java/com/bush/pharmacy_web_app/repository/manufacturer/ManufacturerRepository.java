package com.bush.pharmacy_web_app.repository.manufacturer;

import com.bush.pharmacy_web_app.model.entity.manufacturer.Manufacturer;
import com.bush.pharmacy_web_app.repository.manufacturer.filter.FilterManufacturerRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long>, FilterManufacturerRepository {
    List<Manufacturer> findByNameContainingIgnoreCase(String name);
}

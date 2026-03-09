package com.bush.pharmacy_web_app.repository.manufacturer;

import com.bush.pharmacy_web_app.model.entity.manufacturer.Manufacturer;
import com.bush.pharmacy_web_app.repository.manufacturer.filter.ManufacturerProductCountRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long>, ManufacturerProductCountRepository {
    List<Manufacturer> findByNameContainingIgnoreCase(String name);
}

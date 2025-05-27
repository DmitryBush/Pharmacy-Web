package com.bush.pharmacy_web_app.repository.filter;

import com.bush.pharmacy_web_app.repository.entity.manufacturer.Manufacturer;

import java.util.List;

public interface FilterManufacturerRepository {
    List<Manufacturer> findAllByFilter(ManufacturerFilter filter);
}

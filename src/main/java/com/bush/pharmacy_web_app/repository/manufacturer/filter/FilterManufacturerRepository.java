package com.bush.pharmacy_web_app.repository.manufacturer.filter;

import com.bush.pharmacy_web_app.model.entity.manufacturer.Manufacturer;

import java.util.List;

public interface FilterManufacturerRepository {
    List<Manufacturer> findAllByFilter(ManufacturerFilter filter);
}

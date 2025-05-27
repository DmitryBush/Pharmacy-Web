package com.bush.pharmacy_web_app.repository.filter;

import com.bush.pharmacy_web_app.repository.entity.Supplier;

import java.util.List;

public interface FilterSupplierRepository {
    List<Supplier> findAllByFilter(SupplierFilter filter);
}

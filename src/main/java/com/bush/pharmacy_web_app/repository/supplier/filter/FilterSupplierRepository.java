package com.bush.pharmacy_web_app.repository.supplier.filter;

import com.bush.pharmacy_web_app.model.entity.Supplier;

import java.util.List;

public interface FilterSupplierRepository {
    List<Supplier> findAllByFilter(SupplierFilter filter);
}

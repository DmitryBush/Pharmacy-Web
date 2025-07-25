package com.bush.pharmacy_web_app.repository.supplier;

import com.bush.pharmacy_web_app.model.entity.Supplier;
import com.bush.pharmacy_web_app.repository.supplier.filter.FilterSupplierRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, String>, FilterSupplierRepository {
    List<Supplier> findByNameContainingIgnoreCase(String name);
}

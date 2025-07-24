package com.bush.pharmacy_web_app.service;

import com.bush.pharmacy_web_app.repository.dto.catalog.SupplierReadDto;
import com.bush.pharmacy_web_app.repository.filter.SupplierFilter;
import com.bush.pharmacy_web_app.repository.mapper.admin.SupplierReadMapper;
import com.bush.pharmacy_web_app.repository.mapper.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SupplierService {
    private final SupplierRepository supplierRepository;
    private final SupplierReadMapper supplierReadMapper;

    public List<SupplierReadDto> findAll(SupplierFilter filter) {
        return supplierRepository.findAllByFilter(filter)
                .stream().map(supplierReadMapper::map).toList();
    }

    public List<SupplierReadDto> findByNameContaining(String name) {
        return supplierRepository.findByNameContainingIgnoreCase(name)
                .stream().map(supplierReadMapper::map).toList();
    }
}

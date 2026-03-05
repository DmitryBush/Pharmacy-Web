package com.bush.pharmacy_web_app.service.supplier;

import java.util.List;
import java.util.Optional;

import com.bush.pharmacy_web_app.model.dto.supplier.SupplierCreateDto;
import com.bush.pharmacy_web_app.model.dto.supplier.SupplierReadDto;
import com.bush.pharmacy_web_app.model.entity.Supplier;
import com.bush.pharmacy_web_app.repository.supplier.SupplierRepository;
import com.bush.pharmacy_web_app.repository.supplier.filter.SupplierFilter;
import com.bush.pharmacy_web_app.service.supplier.mapper.SupplierCreateMapper;
import com.bush.pharmacy_web_app.service.supplier.mapper.LegacySupplierReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SupplierService {
    private final SupplierRepository supplierRepository;

    private final SupplierCreateMapper supplierCreateMapper;
    private final LegacySupplierReadMapper legacySupplierReadMapper;

    @Transactional(propagation = Propagation.MANDATORY)
    public SupplierReadDto findOrCreateSupplier(SupplierCreateDto createDto) {
        String itn = createDto.itn();
        return supplierRepository.findById(itn).map(legacySupplierReadMapper::map)
                                 .orElseGet(() -> Optional.of(createDto)
                                                          .map(supplierCreateMapper::map)
                                                          .map(supplierRepository::save)
                                                          .map(legacySupplierReadMapper::map)
                                                          .orElseThrow(() -> new ResponseStatusException(
                                                              HttpStatus.BAD_REQUEST)));
    }

    public Supplier getReferenceById(String itn) {
        return supplierRepository.getReferenceById(itn);
    }

    public List<SupplierReadDto> findAll(SupplierFilter filter) {
        return supplierRepository.findAllByFilter(filter).stream().map(legacySupplierReadMapper::map).toList();
    }

    public List<SupplierReadDto> findByNameContaining(String name) {
        return supplierRepository.findByNameContainingIgnoreCase(name).stream().map(legacySupplierReadMapper::map).toList();
    }
}

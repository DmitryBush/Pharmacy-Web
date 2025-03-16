package com.bush.pharmacy_web_app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CrudOperable<ID, ReadDto, EditDto> {
    List<ReadDto> findAll();
    Page<ReadDto> findAll(Pageable pageable);
    Optional<ReadDto> findById(ID id);
    ReadDto create(EditDto editDto);
    Optional<ReadDto> update(ID id, EditDto editDto);
    boolean delete(ID id);
}

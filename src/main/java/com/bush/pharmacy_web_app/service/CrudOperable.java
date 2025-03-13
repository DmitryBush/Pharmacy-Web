package com.bush.pharmacy_web_app.service;

import java.util.List;
import java.util.Optional;

public interface CrudOperable<ID, ReadDto, EditDto> {
    List<ReadDto> findAll();
    Optional<ReadDto> findById(ID id);
    ReadDto create(EditDto editDto);
    Optional<ReadDto> update(ID id, EditDto editDto);
    boolean delete(ID id);
}

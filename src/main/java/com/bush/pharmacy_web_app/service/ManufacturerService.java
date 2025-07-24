package com.bush.pharmacy_web_app.service;

import com.bush.pharmacy_web_app.repository.dto.manufacturer.ManufacturerReadDto;
import com.bush.pharmacy_web_app.repository.filter.ManufacturerFilter;
import com.bush.pharmacy_web_app.repository.mapper.manufacturer.ManufacturerReadMapper;
import com.bush.pharmacy_web_app.repository.mapper.repository.ManufacturerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;
    private final ManufacturerReadMapper manufacturerReadMapper;

    public List<ManufacturerReadDto> findAll(ManufacturerFilter filter) {
        return manufacturerRepository.findAllByFilter(filter)
                .stream()
                .map(manufacturerReadMapper::map)
                .toList();
    }

    public List<ManufacturerReadDto> findByNameContaining(String name) {
        return manufacturerRepository.findByNameContainingIgnoreCase(name)
                .stream().map(manufacturerReadMapper::map).toList();
    }
}

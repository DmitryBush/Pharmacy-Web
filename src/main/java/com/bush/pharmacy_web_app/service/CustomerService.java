package com.bush.pharmacy_web_app.service;

import com.bush.pharmacy_web_app.repository.CustomerRepository;
import com.bush.pharmacy_web_app.repository.dto.orders.CustomerCreateDto;
import com.bush.pharmacy_web_app.repository.dto.orders.CustomerReadDto;
import com.bush.pharmacy_web_app.repository.mapper.orders.CustomerCreateMapper;
import com.bush.pharmacy_web_app.repository.mapper.orders.CustomerReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerReadMapper readMapper;
    private final CustomerCreateMapper createMapper;

    public List<CustomerReadDto> findAll() {
        return customerRepository.findAll().stream().map(readMapper::map).toList();
    }

    public Page<CustomerReadDto> findAll(Pageable pageable) {
        return customerRepository.findAll(pageable)
                .map(readMapper::map);
    }

    public Optional<CustomerReadDto> findById(String s) {
        return customerRepository.findById(s)
                .map(readMapper::map);
    }

    public CustomerReadDto create(CustomerCreateDto customerCreateDto) {
        return Optional.ofNullable(customerCreateDto)
                .map(createMapper::map)
                .map(customerRepository::save)
                .map(readMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<CustomerReadDto> update(String s, CustomerCreateDto customerCreateDto) {
        return customerRepository.findById(s)
                .map(lamb -> createMapper.map(customerCreateDto, lamb))
                .map(customerRepository::saveAndFlush)
                .map(readMapper::map);
    }

    @Transactional
    public boolean delete(String s) {
        return customerRepository.findById(s)
                .map(lamb -> {
                    customerRepository.delete(lamb);
                    customerRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}

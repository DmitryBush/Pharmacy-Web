package com.bush.pharmacy_web_app.service;

import com.bush.pharmacy_web_app.repository.CustomerRepository;
import com.bush.pharmacy_web_app.repository.dto.CustomerCreateDto;
import com.bush.pharmacy_web_app.repository.dto.CustomerReadDto;
import com.bush.pharmacy_web_app.repository.mapper.CustomerCreateMapper;
import com.bush.pharmacy_web_app.repository.mapper.CustomerReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerService implements CrudOperable<String, CustomerReadDto, CustomerCreateDto>, UserDetailsService {
    private final CustomerRepository customerRepository;
    private final CustomerReadMapper readMapper;
    private final CustomerCreateMapper createMapper;

    @Override
    public List<CustomerReadDto> findAll() {
        return customerRepository.findAll().stream().map(readMapper::map).toList();
    }

    @Override
    public Page<CustomerReadDto> findAll(Pageable pageable) {
        return customerRepository.findAll(pageable)
                .map(readMapper::map);
    }

    @Override
    public Optional<CustomerReadDto> findById(String s) {
        return customerRepository.findById(s)
                .map(readMapper::map);
    }

    @Override
    @Transactional
    public CustomerReadDto create(CustomerCreateDto customerCreateDto) {
        return Optional.ofNullable(customerCreateDto)
                .map(createMapper::map)
                .map(customerRepository::save)
                .map(readMapper::map)
                .orElseThrow();
    }

    @Override
    @Transactional
    public Optional<CustomerReadDto> update(String s, CustomerCreateDto customerCreateDto) {
        return customerRepository.findById(s)
                .map(lamb -> createMapper.map(customerCreateDto, lamb))
                .map(customerRepository::saveAndFlush)
                .map(readMapper::map);
    }

    @Override
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return customerRepository.findById(username)
                .map(customer -> new User(customer.getMobilePhone(),
                        customer.getPassword(), Collections.emptyList()))
                .orElseThrow(() -> new UsernameNotFoundException("Mistake in username or password"));
    }
}

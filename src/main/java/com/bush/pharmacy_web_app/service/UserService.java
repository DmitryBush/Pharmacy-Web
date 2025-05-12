package com.bush.pharmacy_web_app.service;

import com.bush.pharmacy_web_app.repository.UserRepository;
import com.bush.pharmacy_web_app.repository.dto.user.CustomerCreateDto;
import com.bush.pharmacy_web_app.repository.dto.user.CustomerReadDto;
import com.bush.pharmacy_web_app.repository.mapper.user.CustomerCreateMapper;
import com.bush.pharmacy_web_app.repository.mapper.user.CustomerReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final CustomerReadMapper readMapper;
    private final CustomerCreateMapper createMapper;

    public List<CustomerReadDto> findAll() {
        return userRepository.findAll().stream().map(readMapper::map).toList();
    }

    public Page<CustomerReadDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(readMapper::map);
    }

    public Optional<CustomerReadDto> findById(String s) {
        return userRepository.findById(s)
                .map(readMapper::map);
    }

    @Transactional
    public CustomerReadDto create(CustomerCreateDto customerCreateDto) {
        return Optional.ofNullable(customerCreateDto)
                .map(createMapper::map)
                .map(userRepository::save)
                .map(readMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<CustomerReadDto> update(String s, CustomerCreateDto customerCreateDto) {
        return userRepository.findById(s)
                .map(lamb -> createMapper.map(customerCreateDto, lamb))
                .map(userRepository::saveAndFlush)
                .map(readMapper::map);
    }

    @Transactional
    public boolean delete(String s) {
        return userRepository.findById(s)
                .map(lamb -> {
                    userRepository.delete(lamb);
                    userRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findById(username)
                .map(customer -> new User(customer.getMobilePhone(),
                        customer.getPassword(),
                        customer.getRoles()
                                .stream()
                                .map(role -> new SimpleGrantedAuthority("ROLE_"+ role.getType()))
                                .toList()))
                .orElseThrow(() -> new UsernameNotFoundException("Mistake in username or password"));
    }
}

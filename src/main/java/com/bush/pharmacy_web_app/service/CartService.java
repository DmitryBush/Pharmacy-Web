package com.bush.pharmacy_web_app.service;

import com.bush.pharmacy_web_app.repository.CartRepository;
import com.bush.pharmacy_web_app.repository.dto.cart.CartCreateDto;
import com.bush.pharmacy_web_app.repository.dto.cart.CartReadDto;
import com.bush.pharmacy_web_app.repository.mapper.cart.CartCreateMapper;
import com.bush.pharmacy_web_app.repository.mapper.cart.CartReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {
    private final CartRepository cartRepository;
    private final CartReadMapper cartReadMapper;
    private final CartCreateMapper cartCreateMapper;

    public List<CartReadDto> findAll() {
        return cartRepository.findAll()
                .stream()
                .map(cartReadMapper::map)
                .toList();
    }

    public Optional<CartReadDto> findById(Long id) {
        return cartRepository.findById(id)
                .map(cartReadMapper::map);
    }

    @Transactional
    public Optional<CartReadDto> create(CartCreateDto cartCreateDto) {
        return Optional.ofNullable(cartCreateDto)
                .map(cartCreateMapper::map)
                .map(cartRepository::save)
                .map(cartReadMapper::map);
    }

    @Transactional
    public Optional<CartReadDto> update(Long id, CartCreateDto cartCreateDto) {
        return cartRepository.findById(id)
                .map(cart -> cartCreateMapper.map(cartCreateDto, cart))
                .map(cartRepository::saveAndFlush)
                .map(cartReadMapper::map);
    }
}

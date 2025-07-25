package com.bush.pharmacy_web_app.service.user;

import com.bush.pharmacy_web_app.repository.user.CartRepository;
import com.bush.pharmacy_web_app.model.dto.cart.CartCreateDto;
import com.bush.pharmacy_web_app.model.dto.cart.CartReadDto;
import com.bush.pharmacy_web_app.service.cart.mapper.CartCreateMapper;
import com.bush.pharmacy_web_app.service.cart.mapper.CartReadMapper;
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

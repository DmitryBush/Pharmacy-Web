package com.bush.pharmacy_web_app.service.cart;

import com.bush.pharmacy_web_app.model.dto.cart.CartReadDto;
import com.bush.pharmacy_web_app.model.dto.cart.CartUpdateDto;
import com.bush.pharmacy_web_app.model.entity.cart.Cart;
import com.bush.pharmacy_web_app.model.entity.user.User;
import com.bush.pharmacy_web_app.repository.user.cart.CartItemRepository;
import com.bush.pharmacy_web_app.repository.user.cart.CartRepository;
import com.bush.pharmacy_web_app.service.cart.mapper.CartCreateMapper;
import com.bush.pharmacy_web_app.service.cart.mapper.CartReadMapper;
import com.bush.pharmacy_web_app.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    private final UserService userService;

    private final CartCreateMapper cartCreateMapper;
    private final CartReadMapper cartItemReadMapper;

    public CartReadDto findCartByUserId(String userId) {
        return cartRepository.findCartByUserMobilePhone(userId)
                .or(() -> Optional.of(new Cart()))
                .map(cartItemReadMapper::mapToCartReadDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public void changeItemQuantity(String userId, CartUpdateDto updateDto) {
        User user = userService.getUserReferenceById(userId);
        Cart cart = cartRepository.findCartByUserMobilePhone(userId)
                .or(() -> Optional.ofNullable(cartCreateMapper.createCart(user)))
                .map(cartRepository::saveAndFlush)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        cartItemRepository.changeItemQuantity(cart.getId(), updateDto.item().productId(), updateDto.item().quantity());
    }

    @Transactional
    public void deleteItemFromCart(String userId, Long productId) {
        Cart cart = cartRepository.findCartByUserMobilePhone(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        int executionResult = cartItemRepository.deleteItemByCartIdAndProductId(cart.getId(), productId);
        if (executionResult == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}

package com.bush.pharmacy_web_app.controllers.rest.cart;

import com.bush.pharmacy_web_app.model.dto.cart.CartReadDto;
import com.bush.pharmacy_web_app.model.dto.cart.CartUpdateDto;
import com.bush.pharmacy_web_app.service.cart.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartRestController {
    private final CartService cartService;

    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartReadDto> getCartByUserId(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(cartService.findCartByUserId(userDetails.getUsername()));
    }

    @PatchMapping(value = "/me/items", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> changeItemQuantity(@AuthenticationPrincipal UserDetails userDetails,
                                                   @RequestBody @Valid CartUpdateDto updateDto) {
        cartService.changeItemQuantity(userDetails.getUsername(), updateDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/me/items/{productId}")
    public ResponseEntity<Void> removeItemFromCart(@AuthenticationPrincipal UserDetails userDetails,
                                                   @PathVariable Long productId) {
        cartService.deleteItemFromCart(userDetails.getUsername(), productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

package com.bush.pharmacy_web_app.controllers.rest.warehouse;

import com.bush.pharmacy_web_app.repository.dto.warehouse.InventoryReceiptRequestDto;
import com.bush.pharmacy_web_app.repository.dto.warehouse.StorageItemsReadDto;
import com.bush.pharmacy_web_app.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/warehouse")
@RequiredArgsConstructor
public class WarehouseRestController {
    private final StorageService storageService;

    @GetMapping("/branches/{branchId}/products/{productId}/quantity")
    public ResponseEntity<Integer> getProductQuantityAtBranch(@PathVariable Long branchId, @PathVariable Long productId) {
        return ResponseEntity.ok(storageService.getItemQuantityByBranchId(branchId, productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'OPERATOR') and " +
            "@SecurityValidation.checkUserBranchAccess(#userDetails, #productList.branchId)")
    @PostMapping("/inventory-receipts")
    public ResponseEntity<List<StorageItemsReadDto>> createInventoryReceipt(@AuthenticationPrincipal UserDetails userDetails,
                                                                            @RequestBody InventoryReceiptRequestDto productList) {
        try {
            return ResponseEntity.ok(storageService.createInventoryReceiptByBranchId(productList));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}

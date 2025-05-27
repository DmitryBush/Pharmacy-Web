package com.bush.pharmacy_web_app.controllers.rest.warehouse;

import com.bush.pharmacy_web_app.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
}

package com.bush.pharmacy_web_app.controllers.rest.warehouse;

import com.bush.pharmacy_web_app.model.dto.warehouse.TransactionReadDto;
import com.bush.pharmacy_web_app.service.branch.StorageService;
import com.bush.pharmacy_web_app.service.branch.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/warehouse")
@RequiredArgsConstructor
public class WarehouseRestController {
    private final StorageService storageService;
    private final TransactionService transactionService;

    @GetMapping("/branches/{branchId}/products/{productId}/quantity")
    public ResponseEntity<Integer> getProductQuantityAtBranch(@PathVariable Long branchId, @PathVariable Long productId) {
        return ResponseEntity.ok(storageService.getItemQuantityByBranchId(branchId, productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @GetMapping("/branches/{branchId}/transactions")
    public ResponseEntity<List<TransactionReadDto>> getTransactionListAtBranch(@PathVariable Long branchId) {
        return ResponseEntity.ok(transactionService.findAllTransactionsByBranchId(branchId));
    }
}

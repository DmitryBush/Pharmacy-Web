package com.bush.pharmacy_web_app.controllers.rest.warehouse;

import com.bush.pharmacy_web_app.model.dto.warehouse.StorageItemsReadDto;
import com.bush.pharmacy_web_app.model.dto.warehouse.TransactionCreateDto;
import com.bush.pharmacy_web_app.service.branch.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/receipts")
@RequiredArgsConstructor
public class ReceiptRestController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<List<StorageItemsReadDto>> createReceipt(@AuthenticationPrincipal UserDetails userDetails,
                                                                   @RequestBody TransactionCreateDto transactionInfo) {
        try {
            return ResponseEntity.ok(transactionService.createReceiptTransaction(userDetails, transactionInfo));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}

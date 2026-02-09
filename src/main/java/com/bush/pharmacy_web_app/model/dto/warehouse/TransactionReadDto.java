package com.bush.pharmacy_web_app.model.dto.warehouse;

import com.bush.pharmacy_web_app.model.dto.orders.OrderReadDto;
import com.bush.pharmacy_web_app.model.entity.branch.transaction.TransactionName;

import java.time.ZonedDateTime;
import java.util.List;

public record TransactionReadDto(Long transactionId,
                                 ZonedDateTime completedAt,
                                 TransactionName type,
                                 OrderReadDto order,
                                 List<TransactionItemDto> transactionItems) {
}

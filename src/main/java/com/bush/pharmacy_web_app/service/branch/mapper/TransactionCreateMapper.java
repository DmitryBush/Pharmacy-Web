package com.bush.pharmacy_web_app.service.branch.mapper;

import com.bush.pharmacy_web_app.model.dto.warehouse.TransactionCreateDto;
import com.bush.pharmacy_web_app.model.entity.branch.transaction.TransactionHistory;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class TransactionCreateMapper implements DtoMapper<TransactionCreateDto, TransactionHistory> {
    @Value("${time.timezone}")
    private String timezone;
    @Override
    public TransactionHistory map(TransactionCreateDto obj) {
        return copyObj(obj, new TransactionHistory());
    }

    @Override
    public TransactionHistory map(TransactionCreateDto fromObj, TransactionHistory toObj) {
        return copyObj(fromObj, toObj);
    }

    private TransactionHistory copyObj(TransactionCreateDto fromObj, TransactionHistory toObj) {
        toObj.setCompletedAt(ZonedDateTime.now(ZoneId.of(timezone)));
        return toObj;
    }
}

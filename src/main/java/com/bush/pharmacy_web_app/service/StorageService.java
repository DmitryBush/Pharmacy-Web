package com.bush.pharmacy_web_app.service;

import com.bush.pharmacy_web_app.repository.StorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StorageService {
    private final StorageRepository storageRepository;
}

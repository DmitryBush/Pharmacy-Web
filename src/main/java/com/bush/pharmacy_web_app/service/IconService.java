package com.bush.pharmacy_web_app.service;

import com.bush.pharmacy_web_app.service.filesystem.FileSystemStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IconService {
    private final FileSystemStorageService storageService;

    public Resource findAdminIconByName(String name) {
        return storageService.loadAsResource("icons/admin", name);
    }

    public Resource findHeaderIconByName(String name) {
        return storageService.loadAsResource("icons/header", name);
    }
}

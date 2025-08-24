package com.bush.pharmacy_web_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IconService {
    private final FileSystemStorageService storageService;

    public Resource findIconByName(String name, String folder) {
        return storageService.loadAsResource(String.format("icons/%s", folder), name);
    }
}

package com.bush.pharmacy_web_app.service;

import com.bush.pharmacy_web_app.config.StorageConfig;
import com.bush.pharmacy_web_app.service.exception.StorageException;
import com.bush.pharmacy_web_app.service.filesystem.FileSystemStorageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootTest
public class FileSystemStorageServiceTest {
    @Autowired
    private FileSystemStorageService storageService;
    @Autowired
    private StorageConfig config;

    private void prepareDeleteFileTest(StorageConfig config) throws IOException {
        if (config.getDirectory().trim().isEmpty())
            throw new StorageException("Directory for saving files is not specified. Check storage configuration file");
        var root = Paths.get(config.getDirectory(), "file.jpeg").toAbsolutePath();

        Files.createFile(root);
    }

    @Test
    public void DeleteFileTest() {
        try {
            prepareDeleteFileTest(config);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        storageService.delete("file.jpeg");

        Assertions.assertFalse(Files.exists(Paths.get(String.format("%s/file.jpeg", config.getDirectory()))));
    }
}

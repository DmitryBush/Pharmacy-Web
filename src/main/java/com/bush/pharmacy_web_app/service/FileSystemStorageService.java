package com.bush.pharmacy_web_app.service;

import com.bush.pharmacy_web_app.config.StorageConfig;
import com.bush.pharmacy_web_app.service.exception.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.Optional;

@Service
public class FileSystemStorageService {
    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService(StorageConfig config) {
        if (config.getDirectory().trim().isEmpty())
            throw new StorageException("Directory for saving files is not specified. Check storage configuration file");
        rootLocation = Paths.get(config.getDirectory());
    }

    public void store(MultipartFile file) {
        try {
            if (file.isEmpty())
                throw new StorageException("Upload file is empty");
            Path dir = Paths.get(rootLocation.toString(), Optional.ofNullable(file.getOriginalFilename()).orElseThrow())
                    .normalize()
                    .toAbsolutePath();
            if (!dir.getParent().equals(rootLocation.toAbsolutePath())) {
                throw new StorageException("The file being uploaded cannot be located outside the upload area. " +
                        String.format("\nRoot location:%s\nCurrent saved location:%s", rootLocation.toAbsolutePath(), dir));
            }
            try(var inputStream = file.getInputStream()) {
                Files.copy(inputStream, dir, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new StorageException("An error occurred while saving the file ", e);
        }
    }

    public void delete(String filename) {
        try {
            Path file = load(filename);
            Files.delete(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable())
                return resource;
            throw new StorageException(String.format("Unable to read file. Uri res: %s\n Filepath: %s", resource, file));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}

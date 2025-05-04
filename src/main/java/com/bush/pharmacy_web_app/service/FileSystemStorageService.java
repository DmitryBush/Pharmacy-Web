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
            checkEmptyFile(file);
            Path dir = Paths.get(rootLocation.toString(), Optional.ofNullable(file.getOriginalFilename()).orElseThrow())
                    .normalize()
                    .toAbsolutePath();
            validatePath(dir);
            try(var inputStream = file.getInputStream()) {
                Files.copy(inputStream, dir, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new StorageException("An error occurred while saving the file", e);
        }
    }

    public void store(MultipartFile file, String path) {
        try {
            checkEmptyFile(file);
            Path dir = Paths.get(rootLocation.toString(),
                            path, Optional.ofNullable(file.getOriginalFilename()).orElseThrow())
                    .normalize()
                    .toAbsolutePath();
            validatePath(dir);
            if (!Files.exists(load(path)))
                Files.createDirectories(load(path));
            try(var inputStream = file.getInputStream()) {
                Files.copy(inputStream, dir, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new StorageException("An error occurred while saving the file", e);
        }
    }

    private void validatePath(Path dir) {
        if (!dir.startsWith(rootLocation.normalize().toAbsolutePath())) {
            throw new StorageException("The file being uploaded cannot be located outside the upload area. " +
                    String.format("\nRoot location:%s\nCurrent saved location:%s", rootLocation.toAbsolutePath(), dir));
        }
    }

    private static void checkEmptyFile(MultipartFile file) {
        if (file.isEmpty())
            throw new StorageException("Upload file is empty");
    }

    public void delete(String path) {
        try {
            Path file = load(path);
            validatePath(file);
            if (!Files.exists(file))
                throw new NoSuchFileException(String.format("File with directory %s doesn't exists", file));

            Files.delete(file);

            if (!file.getParent().equals(rootLocation)) {
                try (var dirStream = Files.newDirectoryStream(file.getParent())) {
                    if (!dirStream.iterator().hasNext())
                        Files.delete(file.getParent());
                }
            }
        } catch (IOException e) {
            throw new StorageException(e.getMessage());
        }
    }

    public Resource loadAsResource(String path) {
        try {
            Path file = load(path);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable())
                return resource;
            throw new StorageException(String.format("Unable to read file. Uri res: %s\nFilepath: %s\nRoot: %s",
                    resource, file, rootLocation.toAbsolutePath()));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private Path load(String path) {
        return rootLocation.resolve(path).normalize().toAbsolutePath();
    }
}

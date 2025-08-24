package com.bush.pharmacy_web_app.service;

import com.bush.pharmacy_web_app.config.StorageConfig;
import com.bush.pharmacy_web_app.service.exception.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
            String filename = Optional.ofNullable(file.getOriginalFilename())
                    .orElseThrow(() -> new StorageException("Invalid filename"));
            validatePaths(filename);
            checkEmptyFile(file);

            Path resultDir = rootLocation.resolve(filename).normalize().toAbsolutePath();
            validateResultPath(resultDir);
            try(var inputStream = file.getInputStream()) {
                Files.copy(inputStream, resultDir, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new StorageException("An error occurred while saving the file", e);
        }
    }

    public void store(MultipartFile file, String path) {
        try {
            String filename = Optional.ofNullable(file.getOriginalFilename())
                    .orElseThrow(() -> new StorageException("Invalid filename"));
            validatePaths(path, filename);
            checkEmptyFile(file);

            Path resultDir = rootLocation.resolve(path).resolve(filename).normalize().toAbsolutePath();
            validateResultPath(resultDir);
            if (!Files.exists(getValidatedFilePath(path)))
                Files.createDirectories(getValidatedFilePath(path));
            try(var inputStream = file.getInputStream()) {
                Files.copy(inputStream, resultDir, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new StorageException("An error occurred while saving the file", e);
        }
    }

    private void validatePaths(String ...paths) {
        for (var path : paths) {
            if (path.contains("..") || path.contains(".\\") || path.contains("..\\"))
                throw new StorageException("File has invalid name");
        }
    }

    private void validateResultPath(Path resultDir) {
        if (!resultDir.startsWith(rootLocation.normalize().toAbsolutePath() + File.separator)) {
            throw new StorageException("The file being uploaded cannot be located outside the upload area.");
        }
    }

    private static void checkEmptyFile(MultipartFile file) {
        if (file.isEmpty())
            throw new StorageException("Upload file is empty");
    }

    public void delete(String path) {
        try {
            Path filePath = getValidatedFilePath(path);
            if (!Files.exists(filePath))
                throw new NoSuchFileException("File doesn't exists");

            Files.delete(filePath);

            if (!filePath.getParent().equals(rootLocation)) {
                try (var dirStream = Files.newDirectoryStream(filePath.getParent())) {
                    if (!dirStream.iterator().hasNext())
                        Files.delete(filePath.getParent());
                }
            }
        } catch (IOException e) {
            throw new StorageException(e.getMessage());
        }
    }

    public Resource loadAsResource(String path, String filename) {
        try {
            Path filePath = getValidatedFilePath(path, filename);

            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable())
                return resource;

            throw new StorageException("Unable to read file");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private Path getValidatedFilePath(String path, String filename) {
        validatePaths(path, filename);
        Path resultPath = rootLocation.resolve(path).resolve(filename).normalize().toAbsolutePath();
        validateResultPath(resultPath);
        return resultPath;
    }

    private Path getValidatedFilePath(String path) {
        Path resultPath = rootLocation.resolve(path).normalize().toAbsolutePath();
        validateResultPath(resultPath);
        return resultPath;
    }
}

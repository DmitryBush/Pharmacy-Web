package com.bush.pharmacy_web_app.service;

import com.bush.pharmacy_web_app.config.StorageConfig;
import com.bush.pharmacy_web_app.service.exception.StorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.Optional;

@Service
public class FileSystemStorageService {
    private final Logger logger = LoggerFactory.getLogger(FileSystemStorageService.class);

    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService(StorageConfig config) {
        if (config.getDirectory().trim().isEmpty())
            throw new StorageException("Directory for saving files is not specified. Check storage configuration file");
        rootLocation = Paths.get(config.getDirectory()).normalize();
    }

    public void store(MultipartFile file) {
        try {
            Path resultDir = getValidatedFilePath(file);

            try(var inputStream = file.getInputStream()) {
                Files.copy(inputStream, resultDir, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new StorageException("An error occurred while saving the file", e);
        }
    }

    public void store(MultipartFile file, String path) {
        try {
            Path resultDir = getValidatedFilePath(path, file);

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
            var segments = path.split("[\\\\/]");
            for (var segment: segments) {
                if (segment.isEmpty()) {
                    logger.error("Find empty segment ${}", Arrays.stream(segments).toList());
                    throw new StorageException("Empty segment");
                } else if (segment.length() > 255) {
                    throw new StorageException("Segment length doesn't match maximum OS folder or file length");
                } else if (!segment.matches("(^[a-zA-Z0-9_-]+\\.[a-zA-Z0-9]+$)|(^[a-zA-Z0-9_-]+$)")) {
                    logger.error("Segment has invalid name: ${}", segment);
                    throw new StorageException("Segment has invalid name");
                }
            }
        }
    }

    private void validatePaths(Path ...paths) {
        for (var path : paths) {
            var segments = path.toString().split("[\\\\/]");
            for (var segment: segments) {
                if (segment.isEmpty()) {
                    logger.error("Find empty segment ${}", Arrays.stream(segments).toList());
                    throw new StorageException("Empty segment");
                } else if (segment.length() > 255) {
                    throw new StorageException("Segment length doesn't match maximum OS folder or file length");
                } else if (!segment.matches("(^[a-zA-Z0-9_-]+\\.[a-zA-Z0-9]+$)|(^[a-zA-Z0-9_-]+$)")) {
                    logger.error("Segment has invalid name: ${}", segment);
                    throw new StorageException("Segment has invalid name");
                }
            }
        }
    }

    private void validateResultPath(Path resultDir) {
        if (!resultDir.startsWith(rootLocation.toAbsolutePath() + File.separator)) {
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

    private Path getValidatedFilePath(String path, MultipartFile file) {
        checkEmptyFile(file);
        Path filename = Paths.get(Optional.ofNullable(file.getOriginalFilename())
                .orElseThrow(() -> new StorageException("Invalid filename"))).normalize();
        Path normalizedPath = Paths.get(Optional.ofNullable(path)
                .orElseThrow(() -> new StorageException("Invalid path"))).normalize();
        validatePaths(normalizedPath, filename);
        Path resultPath = rootLocation.resolve(path).resolve(filename).normalize().toAbsolutePath();
        validateResultPath(resultPath);
        return resultPath;
    }

    private Path getValidatedFilePath(MultipartFile file) {
        checkEmptyFile(file);
        Path filename = Paths.get(Optional.ofNullable(file.getOriginalFilename())
                .orElseThrow(() -> new StorageException("Invalid filename"))).normalize();
        validatePaths(filename);
        Path resultPath = rootLocation.resolve(filename).normalize().toAbsolutePath();
        validateResultPath(resultPath);
        return resultPath;
    }

    private Path getValidatedFilePath(String path, String name) {
        Path filename = Paths.get(Optional.ofNullable(name)
                .orElseThrow(() -> new StorageException("Invalid filename"))).normalize();
        Path normalizedPath = Paths.get(Optional.ofNullable(path)
                .orElseThrow(() -> new StorageException("Invalid path"))).normalize();
        validatePaths(normalizedPath, filename);
        Path resultPath = rootLocation.resolve(path).resolve(filename).normalize().toAbsolutePath();
        validateResultPath(resultPath);
        return resultPath;
    }

    private Path getValidatedFilePath(String path) {
        Path normalizedPath = Paths.get(Optional.ofNullable(path)
                .orElseThrow(() -> new StorageException("Invalid path"))).normalize();
        validatePaths(normalizedPath);
        Path resultPath = rootLocation.resolve(path).normalize().toAbsolutePath();
        validateResultPath(resultPath);
        return resultPath;
    }
}

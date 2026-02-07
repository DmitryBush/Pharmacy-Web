package com.bush.pharmacy_web_app.service.filesystem;

import com.bush.pharmacy_web_app.service.exception.StorageException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;

@Service
@RequiredArgsConstructor
public class FileSystemStorageService {
    private final Logger logger = LoggerFactory.getLogger(FileSystemStorageService.class);

    private final FilePathBuilder filePathBuilder;

    private final Path rootLocation;

    @Deprecated
    public void store(MultipartFile file) {
        try {
            checkEmptyFile(file);
            Path resultDir = filePathBuilder.getValidatedFilePath("", file.getOriginalFilename());

            try(var inputStream = file.getInputStream()) {
                Files.copy(inputStream, resultDir, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new StorageException("An error occurred while saving the file", e);
        }
    }

    @Deprecated
    public void store(MultipartFile file, String path) {
        try {
            checkEmptyFile(file);
            Path resultDir = filePathBuilder.getValidatedFilePath(path, file.getOriginalFilename());
            if (!Files.exists(filePathBuilder.getValidatedFilePath(path)))
                Files.createDirectories(filePathBuilder.getValidatedFilePath(path));
            try(var inputStream = file.getInputStream()) {
                Files.copy(inputStream, resultDir, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new StorageException("An error occurred while saving the file", e);
        }
    }

    public void store(MultipartFile file, String filename, String path) {
        try {
            checkEmptyFile(file);
            Path resultDir = filePathBuilder.getValidatedFilePath(path, filename);
            if (!Files.exists(filePathBuilder.getValidatedFilePath(path)))
                Files.createDirectories(filePathBuilder.getValidatedFilePath(path));
            try(var inputStream = file.getInputStream()) {
                Files.copy(inputStream, resultDir, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new StorageException("An error occurred while saving the file", e);
        }
    }

    public void storeByFullPath(MultipartFile file, String fullPath) {
        try {
            checkEmptyFile(file);
            Path resultDir = filePathBuilder.getValidatedFilePath(fullPath);
            if (!Files.exists(filePathBuilder.getValidatedFilePath(fullPath)))
                Files.createDirectories(filePathBuilder.getValidatedFilePath(fullPath));
            try(var inputStream = file.getInputStream()) {
                Files.copy(inputStream, resultDir, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new StorageException("An error occurred while saving the file", e);
        }
    }

    private static void checkEmptyFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new StorageException("Upload file is empty");
        }
    }

    public void delete(String path) {
        try {
            Path filePath = filePathBuilder.getValidatedFilePath(path);
            if (!Files.exists(filePath))
                throw new NoSuchFileException("File doesn't exists");

            Files.delete(filePath);

            if (!filePath.getParent().equals(rootLocation)) {
                try (var dirStream = Files.newDirectoryStream(filePath.getParent())) {
                    if (!dirStream.iterator().hasNext()) {
                        Files.delete(filePath.getParent());
                    }
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new StorageException(e.getMessage());
        }
    }

    public Resource loadAsResource(String path, String filename) {
        try {
            Path filePath = filePathBuilder.getValidatedFilePath(path, filename);

            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }

            logger.error(filePath.toString());
            throw new StorageException("Unable to read file");
        } catch (MalformedURLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Resource loadAsResource(String path) {
        try {
            Path filePath = filePathBuilder.getValidatedFilePath(path);

            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }

            logger.error(filePath.toString());
            throw new StorageException("Unable to read file");
        } catch (MalformedURLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

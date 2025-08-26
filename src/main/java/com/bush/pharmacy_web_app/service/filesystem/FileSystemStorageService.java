package com.bush.pharmacy_web_app.service.filesystem;

import com.bush.pharmacy_web_app.service.exception.StorageException;
import lombok.RequiredArgsConstructor;
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
    private final FilePathBuilder filePathBuilder;

    private final Path rootLocation;

    public void store(MultipartFile file) {
        try {
            checkEmptyFile(file);
            Path resultDir = filePathBuilder.getValidatedFilePath("", file.getOriginalFilename());

            try(var inputStream = file.getInputStream()) {
                Files.copy(inputStream, resultDir, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new StorageException("An error occurred while saving the file", e);
        }
    }

    public void store(MultipartFile file, String path) {
        try {
            checkEmptyFile(file);
            Path resultDir = filePathBuilder.getValidatedFilePath(path, file.getOriginalFilename());

            if (!Files.exists(resultDir))
                Files.createDirectories(resultDir);
            try(var inputStream = file.getInputStream()) {
                Files.copy(inputStream, resultDir, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
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

            throw new StorageException("Unable to read file");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}

package com.bush.pharmacy_web_app.validation.path;

import com.bush.pharmacy_web_app.service.exception.StorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileNameValidator {
    @Value("${storage.max-path-segment-length:255}")
    private Short MAX_PATH_SEGMENT_LENGTH;

    public Path getValidatedFileNamePath(String filename) {
        validateFileName(filename);
        return Paths.get(filename).normalize();
    }

    private void validateFileName(String filename) {
        if (filename == null) {
            throw new StorageException("Invalid filename");
        }
        if (filename.length() > MAX_PATH_SEGMENT_LENGTH || filename.isBlank()) {
            throw new StorageException("File length doesn't match maximum OS file length or blank");
        }
        if (filename.contains("..") || filename.contains("/")
                || filename.contains("\\")) {
            throw new StorageException("File has invalid characters");
        }
    }
}

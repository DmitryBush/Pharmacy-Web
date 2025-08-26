package com.bush.pharmacy_web_app.validation.path;

import com.bush.pharmacy_web_app.service.exception.StorageException;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Component
public class FileNameValidator {

    public Path getValidatedFileNamePath(String filename) {
        var filenamePath = Paths.get(Optional.ofNullable(filename)
                .orElseThrow(() -> new StorageException("Invalid filename"))).normalize();
        validateFileName(filenamePath);
        return filenamePath;
    }

    protected void validateFileName(Path filename) {
        if (filename.toString().length() > 255) {
            throw new StorageException("File length doesn't match maximum OS file length");
        } else if (filename.toString().contains("..") || filename.toString().contains("/")
                || filename.toString().contains("\\")) {
            throw new StorageException("File has invalid characters");
        }
    }
}

package com.bush.pharmacy_web_app.validation.path;

import com.bush.pharmacy_web_app.service.exception.StorageException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;

@Component
@RequiredArgsConstructor
public class PathValidator {
    @Value("${storage.max-path-length:4096}")
    private Short MAX_PATH_LENGTH;
    @Value("${storage.max-path-segment-length:255}")
    private Short MAX_PATH_SEGMENT_LENGTH;

    private final Logger logger = LoggerFactory.getLogger(PathValidator.class);

    private final Path rootLocation;

    public void validatePaths(Path...paths) {
        for (var path : paths) {
            validatePaths(path.toString());
        }
    }

    public void validatePaths(String ...paths) {
        for (var path : paths) {
            if (path == null) {
                throw new StorageException("Invalid filename");
            }
            if (rootLocation.toString().length() + path.length() > MAX_PATH_LENGTH) {
                throw new StorageException("Path length doesn't match maximum OS path length");
            }

            var segments = path.split("[\\\\/]");
            for (var segment : segments) {
                if (segment.isEmpty()) {
                    logger.error("Find empty segment");
                    throw new StorageException("Empty segment");
                } else if (segment.length() > MAX_PATH_SEGMENT_LENGTH) {
                    throw new StorageException("Segment length doesn't match maximum OS folder or file length");
                } else if (!segment.matches("^[a-zA-Z0-9_-]+$")) {
                    logger.error("Segment has invalid name");
                    throw new StorageException("Segment has invalid name");
                }
            }
        }
    }

    public void validateRelativelyRootPath(Path ...paths) {
        for (var path : paths) {
            var absolutePath = path.toAbsolutePath();
            if (!absolutePath.startsWith(rootLocation + File.separator)) {
                throw new StorageException("The file cannot be located outside the upload area.");
            }
        }
    }
}

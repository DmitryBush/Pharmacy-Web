package com.bush.pharmacy_web_app.service.filesystem;

import com.bush.pharmacy_web_app.service.exception.StorageException;
import com.bush.pharmacy_web_app.validation.path.FileNameValidator;
import com.bush.pharmacy_web_app.validation.path.PathValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FilePathBuilder {
    private final FileNameValidator fileNameValidator;
    private final PathValidator pathValidator;

    private final Path rootLocation;

    public Path getValidatedFilePath(String path, String fileName) {
        Path filenamePath = fileNameValidator.getValidatedFileNamePath(fileName);
        Path normalizedPath = getValidatedFilePath(path);

        var resolvedPath = normalizedPath.resolve(filenamePath).normalize().toAbsolutePath();
        pathValidator.validateRelativelyRootPath(resolvedPath);
        return resolvedPath;
    }

    public Path getValidatedFilePath(String path) {
        pathValidator.validatePaths(path);
        Path normalizedPath = Paths.get(path).normalize();
        return buildValidatedPath(normalizedPath);
    }

    private Path buildValidatedPath(Path ...paths) {
        var resultPath = rootLocation;
        for (var path : paths) {
            resultPath = resultPath.resolve(path).normalize().toAbsolutePath();
            pathValidator.validateRelativelyRootPath(resultPath);
        }
        return resultPath;
    }
}

package com.bush.pharmacy_web_app.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

public class ImageFileValidator implements ConstraintValidator<ImageFile, List<MultipartFile>> {
    private String[] allowedTypes;
    @Override
    public void initialize(ImageFile constraintAnnotation) {
        allowedTypes = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(List<MultipartFile> multipartFiles, ConstraintValidatorContext constraintValidatorContext) {
        if (multipartFiles == null)
            return true;
        if (multipartFiles.isEmpty())
            return true;

        Set<String> typeSet = new HashSet<>(Arrays.asList(allowedTypes));

        return multipartFiles.stream()
                .allMatch(file -> {
                    if (file.isEmpty()) {
                        return true;
                    }

                    String contentType = file.getContentType();
                    return contentType != null && typeSet.contains(contentType);
                });
    }
}

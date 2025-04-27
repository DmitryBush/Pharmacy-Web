package com.bush.pharmacy_web_app.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ImageFileValidator implements ConstraintValidator<ImageFile, List<MultipartFile>> {
    private String[] allowedTypes;
    @Override
    public void initialize(ImageFile constraintAnnotation) {
        allowedTypes = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(List<MultipartFile> multipartFiles, ConstraintValidatorContext constraintValidatorContext) {
        if (multipartFiles.isEmpty())
            return true;

        Set<String> typeSet = new HashSet<>(Arrays.asList(allowedTypes));

        return multipartFiles.stream()
                .allMatch(file -> {
                    String type = file.getContentType();
                    return typeSet.contains(type);
                });
    }
}

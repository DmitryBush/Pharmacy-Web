package com.bush.pharmacy_web_app.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SlugValidator implements ConstraintValidator<Slug, String> {
    private final Pattern validationPattern = Pattern.compile("^[a-z0-9]+(?:-[a-z0-9]+)*$");

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(s)) {
            return false;
        }
        if (s.isBlank()) {
            return false;
        }
        final Matcher matcher = validationPattern.matcher(s);
        return matcher.matches();
    }
}

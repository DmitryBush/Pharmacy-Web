package com.bush.pharmacy_web_app.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Constraint(validatedBy = ImageFileValidator.class)
public @interface ImageFile {
    String[] value();
    String message() default "Изображение должно быть в формате jpeg, png или webm";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

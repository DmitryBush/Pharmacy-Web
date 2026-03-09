package com.bush.pharmacy_web_app.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Constraint(validatedBy = SlugValidator.class)
public @interface Slug {
    String message() default "Slug должен содержать только буквы латинского алфавита, "
            + "цифры и дефис в качестве разделителя слов";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

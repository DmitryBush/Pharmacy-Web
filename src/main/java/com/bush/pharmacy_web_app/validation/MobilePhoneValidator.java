package com.bush.pharmacy_web_app.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class MobilePhoneValidator implements ConstraintValidator<MobilePhone, String> {
    private final static Pattern ruPhone = Pattern.compile("^(\\+7|8)\\s\\(\\d{3}\\)\\s\\d{3}-\\d{2}-\\d{2}$");
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s.length() < 17 || s.length() > 18)
            return false;
        return ruPhone.matcher(s).matches();
    }
}

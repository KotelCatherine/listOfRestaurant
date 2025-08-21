package com.restaurants.api.modules.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<PhoneValidation, String> {

    private static final String PHONE_PATTERN =
            "^((\\+7|8)+([0-9]){10})$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

        if (value == null || value.isBlank()) {

            return true;

        }

        String trimmedValue = value.trim();

        return trimmedValue.matches(PHONE_PATTERN);

    }

    @Override
    public void initialize(PhoneValidation constraintAnnotation) {

    }

}

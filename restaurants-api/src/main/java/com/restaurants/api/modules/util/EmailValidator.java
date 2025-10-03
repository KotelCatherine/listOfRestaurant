package com.restaurants.api.modules.util;

// Указывает, что аннотация может применяться к полям класса и параметрам метода

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class EmailValidator implements ConstraintValidator<EmailValidation, String> {

    // Регулярное выражение для базовой проверки валидности email.

    // Учитывает:

    // - допустимые символы в local-part (до @)

    // - наличие домена и TLD (после @)

    // - корректный формат без лишних спецсимволов

    private static final String EMAIL_PATTERN =
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    // Метод вызывается один раз при инициализации валидатора.

    // В нашем случае аннотация не содержит параметров, поэтому ничего не инициализируется.

    @Override

    public void initialize(EmailValidation constraintAnnotation) {

        // Не требуется инициализация параметров

    }

    // Основной метод, реализующий логику проверки валидности email-значения

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Null и пустые строки считаются допустимыми значениями.

        // Это поведение согласуется с подходом "аннотация делает одну задачу" —

        // Если требуется обязательное поле, используйте @NotNull или @NotBlank отдельно.

        if (value == null || value.isBlank()) {
            return true;
        }

        // Проверка соответствия регулярному выражению
        String trimmedValue = value.trim();

        return trimmedValue.matches(EMAIL_PATTERN);

    }

}




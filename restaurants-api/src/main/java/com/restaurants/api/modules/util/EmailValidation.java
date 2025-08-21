package com.restaurants.api.modules.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

// Указывает, что аннотация может применяться к полям класса и параметрам метода
@Target({FIELD, PARAMETER})

// Аннотация будет сохраняться в байткоде и доступна во время выполнения через Reflection API
@Retention(RUNTIME)

// Помечает аннотацию как документируемую (будет отображаться в JavaDoc)
@Documented

// Указывает, какой класс будет использоваться для валидации значений, аннотированных данной аннотацией
@Constraint(validatedBy = {EmailValidator.class})
public @interface EmailValidation {

    // Сообщение об ошибке, выводимое при нарушении валидации
    String message() default "Invalid email format";

    // Определение групп валидации (обычно используется при сложных сценариях валидации)
    Class<?>[] groups() default {};

    // Поле для передачи дополнительной информации об ошибке — используется редко
    Class<? extends Payload>[] payload() default {};

}

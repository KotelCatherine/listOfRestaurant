package com.restaurants.api.modules.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {PhoneValidator.class})
public @interface PhoneValidation {

    // Сообщение об ошибке, выводимое при нарушении валидации
    String message() default "Invalid email format";

    // Определение групп валидации (обычно используется при сложных сценариях валидации)
    Class<?>[] groups() default {};

    // Поле для передачи дополнительной информации об ошибке — используется редко
    Class<? extends Payload>[] payload() default {};

}

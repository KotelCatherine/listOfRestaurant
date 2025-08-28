package com.restaurants.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@Accessors(fluent = true)
public enum RestaurantErrorCodeEnum {

    INTERNAL_SERVER_ERROR("00", "Ошибка сервиса управления данными", HttpStatus.INTERNAL_SERVER_ERROR),
    WRONG_OBJECT_PARAMS("01", "Неверные параметры объекта", HttpStatus.BAD_REQUEST),
    RESTAURANT_NAME_ALREADY_EXISTS("03", "Ресторан с таким название уже существует", HttpStatus.BAD_REQUEST),
    NOT_FOUND_RESTAURANT_BY_ID("04", "Ресторан не найден", HttpStatus.NOT_FOUND),
    NOT_FOUND_CUISINE_BY_ID("05", "Кухня не найдена", HttpStatus.NOT_FOUND);

    private final String errorCode;
    private final String description;
    private final HttpStatus status;

}

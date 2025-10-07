package com.restaurants.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@Accessors(fluent = true)
public enum MenuItemErrorCodeEnum {

    INTERNAL_SERVER_ERROR("00", "Ошибка сервиса управления данными", HttpStatus.INTERNAL_SERVER_ERROR),
    WRONG_OBJECT_PARAMS("01", "Неверные параметры объекта", HttpStatus.BAD_REQUEST),
    MENU_ITEM_NAME_ALREADY_EXISTS("03", "Пункт меню с таким названием уже существует", HttpStatus.BAD_REQUEST),
    NOT_FOUND_MENU_ITEM_BY_ID("04", "Пункт меню не найден", HttpStatus.NOT_FOUND);

    private final String errorCode;
    private final String description;
    private final HttpStatus status;

}

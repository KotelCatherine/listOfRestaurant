package com.restaurants.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@Accessors(fluent = true)
public enum RestaurantScheduleErrorCodeEnum {

    INTERNAL_SERVER_ERROR("00", "Ошибка сервиса управления данными", HttpStatus.INTERNAL_SERVER_ERROR),
    WRONG_OBJECT_PARAMS("01", "Неверные параметры объекта", HttpStatus.BAD_REQUEST),
    NOT_FOUND_RESTAURANT_BY_ID("03", "Ресторан не найден", HttpStatus.NOT_FOUND),
    NOT_FOUND_RESTAURANT_SCHEDULE_BY_ID("04", "Расписание не найдено", HttpStatus.NOT_FOUND);

    private final String errorCode;
    private final String description;
    private final HttpStatus status;

}

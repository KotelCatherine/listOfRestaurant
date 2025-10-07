package com.restaurants.api.resource;

import com.restaurants.api.exception.RestaurantScheduleException;
import com.restaurants.api.modules.restaurant.dto.RestaurantScheduleDto;
import com.restaurants.api.modules.restaurant.request.RestaurantScheduleRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "RestaurantSchedule", description = "Расписание ресторана")
public interface RestaurantScheduleResource {

    @PostMapping
    @Operation(operationId = "createRestaurantScheduleUsingPost", summary = "Создание нового расписания")
    @ResponseStatus(HttpStatus.CREATED)
    RestaurantScheduleDto create(@RequestBody RestaurantScheduleRequest request);

    @GetMapping("/{id}")
    @Operation(operationId = "findByIdUsingGet", summary = "Поиск расписания по идентификатору")
    @ResponseStatus(HttpStatus.OK)
    RestaurantScheduleDto findById(@PathVariable UUID id) throws RestaurantScheduleException;

    @GetMapping("/{restaurantId}/restaurantSchedule")
    @Operation(operationId = "findCompleteHoursRestaurantUsingGet", summary = "Полное расписание ресторана")
    @ResponseStatus(HttpStatus.OK)
    List<RestaurantScheduleDto> findCompleteHoursRestaurant(@PathVariable UUID restaurantId) throws RestaurantScheduleException;

    @PutMapping("/{id}")
    @Operation(operationId = "updateRestaurantScheduleUsingPut", summary = "Обновление расписания")
    @ResponseStatus(HttpStatus.OK)
    RestaurantScheduleDto update(@PathVariable UUID id, @RequestBody RestaurantScheduleRequest request) throws RestaurantScheduleException;

    @DeleteMapping("/{id}")
    @Operation(operationId = "deleteByIdUsingDelete", summary = "Удаление расписания")
    @ResponseStatus(HttpStatus.OK)
    void delete(@PathVariable UUID id) throws RestaurantScheduleException;

}

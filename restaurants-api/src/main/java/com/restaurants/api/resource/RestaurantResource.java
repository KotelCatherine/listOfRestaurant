package com.restaurants.api.resource;

import com.restaurants.api.exception.RestaurantException;
import com.restaurants.api.modules.restaurant.dto.FindRestaurantDto;
import com.restaurants.api.modules.restaurant.dto.RestaurantDto;
import com.restaurants.api.modules.restaurant.request.CreateRestaurantRequest;
import com.restaurants.api.modules.restaurant.request.UpdateRestaurantRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/*  – Это интерфейс, помеченный аннотацией @FeignClient, который определяет API для работы с ресторанами.
 Он включает метод create, который принимает объект CreateRestaurantRequest и возвращает объект RestaurantDto.
 Этот интерфейс используется для взаимодействия с REST API, обеспечивая возможность отправки
 HTTP-запросов на создание нового ресторана.*/

@FeignClient(
        name = "restaurant-client",
        path = "/restaurant",
        url = "${app.restaurant.url}"
)
@Tag(name = "Restaurant", description = "Рестораны")
public interface RestaurantResource {

    @PostMapping
    @Operation(operationId = "createRestaurantUsingPost", summary = "Создание ресторана")
    @ResponseStatus(HttpStatus.CREATED)
    RestaurantDto create(@RequestBody @Valid CreateRestaurantRequest request) throws RestaurantException;

    @GetMapping("/{id}")
    @Operation(operationId = "findRestaurantUsingGet", summary = "Поиск ресторана")
    FindRestaurantDto findRestaurant(@PathVariable UUID id) throws RestaurantException;

    @GetMapping
    @Operation(summary = "Получить список ресторанов")
    Page<RestaurantDto> getAllRestaurants(@ParameterObject Pageable pageable);

    @GetMapping("/search")
    @Operation(summary = "Поиск ресторанов по названию")
    List<RestaurantDto> searchRestaurantsByName(
            @RequestParam String nameQuery);

    @PostMapping("/{id}")
    @Operation(operationId = "updateRestaurantUsingPost", summary = "Обновление ресторана")
    RestaurantDto update(@PathVariable UUID id, @RequestBody UpdateRestaurantRequest request) throws RestaurantException;

    @DeleteMapping("/{id}")
    @Operation(operationId = "deleteRestaurantUsingDelete", summary = "Удаление ресторана")
    void delete(@PathVariable UUID id);

}

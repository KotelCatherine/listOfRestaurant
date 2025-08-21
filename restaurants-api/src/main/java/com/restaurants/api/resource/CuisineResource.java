package com.restaurants.api.resource;

import com.restaurants.api.modules.restaurant.dto.CuisineDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.UUID;


@FeignClient(
        name = "restaurant-client",
        path = "/restaurant",
        url = "${app.restaurant.url}"
)
@Tag(name = "Cuisine", description = "Кухни")
public interface CuisineResource {

    @GetMapping("/cuisines")
    @Operation(operationId = "getAllCuisines", summary = "Получить все типы кухонь")
    List<CuisineDto> getAllCuisines();

    @PostMapping("/{restaurantId}/cuisines/{cuisineId}")
    @Operation(operationId = "addCuisineToRestaurant", summary = "Добавить кухню к ресторану")
    void addCuisine(@PathVariable UUID restaurantId, @PathVariable UUID cuisineId);

    @DeleteMapping("/{restaurantId}/cuisines/{cuisineId}")
    @Operation(operationId = "removeCuisineFromRestaurant", summary = "Удалить кухню из ресторана")
    void removeCuisine(@PathVariable UUID restaurantId, @PathVariable UUID cuisineId);

}

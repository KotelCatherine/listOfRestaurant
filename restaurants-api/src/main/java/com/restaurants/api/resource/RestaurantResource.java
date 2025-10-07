
package com.restaurants.api.resource;

import com.restaurants.api.exception.CuisineException;
import com.restaurants.api.exception.RestaurantException;
import com.restaurants.api.modules.restaurant.dto.AddressDto;
import com.restaurants.api.modules.restaurant.dto.CuisineDto;
import com.restaurants.api.modules.restaurant.dto.MenuCategoryDto;
import com.restaurants.api.modules.restaurant.dto.RestaurantDto;
import com.restaurants.api.modules.restaurant.request.AddressRequest;
import com.restaurants.api.modules.restaurant.request.RestaurantRequest;
import com.restaurants.api.modules.restaurant.request.MenuCategoryRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Restaurant", description = "Рестораны")
public interface RestaurantResource {

    @PostMapping
    @Operation(operationId = "createRestaurantUsingPost", summary = "Создание ресторана")
    @ResponseStatus(HttpStatus.CREATED)
    RestaurantDto create(@RequestBody @Valid RestaurantRequest request) throws RestaurantException;

    @GetMapping("/{id}")
    @Operation(operationId = "findRestaurantUsingGet", summary = "Поиск ресторана")
    @ResponseStatus(HttpStatus.OK)
    RestaurantDto findRestaurant(@PathVariable UUID id) throws RestaurantException;

    @GetMapping
    @Operation(summary = "Получить список ресторанов")
    @ResponseStatus(HttpStatus.OK)
    Page<RestaurantDto> getAllRestaurants(@ParameterObject Pageable pageable);

    @GetMapping("/search")
    @Operation(summary = "Поиск ресторанов по названию")
    @ResponseStatus(HttpStatus.OK)
    List<RestaurantDto> searchRestaurantsByName(
            @RequestParam String nameQuery);

    @PutMapping("/{id}")
    @Operation(operationId = "updateRestaurantUsingPost", summary = "Обновление ресторана")
    @ResponseStatus(HttpStatus.CREATED)
    RestaurantDto update(@PathVariable UUID id, @RequestBody RestaurantRequest request) throws RestaurantException;

    @DeleteMapping("/{id}")
    @Operation(operationId = "deleteRestaurantUsingDelete", summary = "Удаление ресторана")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable UUID id) throws RestaurantException;

    @GetMapping("/{id}/cuisines")
    @Operation(operationId = "findAllCuisineByRestaurantId", summary = "Список кухонь ресторана")
    @ResponseStatus(HttpStatus.OK)
    List<CuisineDto> findAllCuisinesByRestaurantId(@Parameter(description = "Id ресторана") @PathVariable UUID id) throws RestaurantException;

    @GetMapping("/{cuisineId}/restaurant")
    @Operation(operationId = "findAllRestaurantByCuisineId", summary = "Список ресторанов по кухне")
    @ResponseStatus(HttpStatus.OK)
    List<RestaurantDto> findAllRestaurantByCuisineId(@Parameter(description = "Id кухни") @PathVariable UUID cuisineId) throws RestaurantException;

    @PostMapping("/cuisines/{restaurantId}/{cuisineId}")
    @Operation(operationId = "createRestaurantCuisineUsingPost", summary = "Создание связи между кухней и рестораном")
    @ResponseStatus(HttpStatus.CREATED)
    CuisineDto createRestaurantCuisine(@PathVariable UUID restaurantId, @PathVariable UUID cuisineId) throws RestaurantException, CuisineException;

    @PostMapping("/{restaurantId}/address")
    @Operation(operationId = "createAddressByRestaurantIdUsingPost", summary = "Создание адреса у ресторана")
    @ResponseStatus(HttpStatus.CREATED)
    AddressDto createAddress(@PathVariable UUID restaurantId, @Valid @RequestBody AddressRequest request) throws RestaurantException;

    @PostMapping("/{restaurantId}/menuCategory")
    @Operation(operationId = "createMenuCategoryUsingPost", summary = "Создание категории меню")
    @ResponseStatus(HttpStatus.CREATED)
    MenuCategoryDto createMenuCategory(@PathVariable UUID restaurantId, @Valid @RequestBody MenuCategoryRequest request) throws RestaurantException;

}

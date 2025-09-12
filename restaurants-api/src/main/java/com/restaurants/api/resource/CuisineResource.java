package com.restaurants.api.resource;

import com.restaurants.api.exception.CuisineException;
import com.restaurants.api.modules.restaurant.dto.CuisineDto;
import com.restaurants.api.modules.restaurant.request.CreateCuisineRequest;
import com.restaurants.api.modules.restaurant.request.UpdateCuisineRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@FeignClient(
        name = "cuisine-client",
        path = "/cuisine",
        url = "${app.cuisine.url}"
)
@Tag(name = "Cuisine", description = "Кухни")
public interface CuisineResource {

    @GetMapping("/cuisines")
    @Operation(operationId = "getAllCuisines", summary = "Получить все типы кухонь")
    Page<CuisineDto> getAllCuisines(@ParameterObject Pageable pageable);

    @PostMapping
    @Operation(operationId = "addCuisine", summary = "Добавить кухню")
    CuisineDto createCuisine(@Valid @RequestBody CreateCuisineRequest request) throws CuisineException;

    @PostMapping("/{id}")
    @Operation(operationId = "updateCuisine", summary = "Обновить кухню")
    CuisineDto updateCuisine(@Valid @RequestBody UpdateCuisineRequest request);

    @DeleteMapping("/{id}")
    @Operation(operationId = "removeCuisine", summary = "Удалить кухню")
    void deleteCuisine(@PathVariable UUID id);

}

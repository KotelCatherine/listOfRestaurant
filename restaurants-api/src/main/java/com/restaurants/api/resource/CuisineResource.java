package com.restaurants.api.resource;

import com.restaurants.api.exception.CuisineException;
import com.restaurants.api.modules.restaurant.dto.CuisineDto;
import com.restaurants.api.modules.restaurant.request.CuisineRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Cuisine", description = "Кухни")
public interface CuisineResource {

    @PostMapping
    @Operation(operationId = "addCuisine", summary = "Добавить кухню")
    @ResponseStatus(HttpStatus.CREATED)
    CuisineDto createCuisine(@Valid @RequestBody CuisineRequest request) throws CuisineException;

    @GetMapping("/{id}")
    @Operation(operationId = "findCuisinesByIdUsingGet", summary = "Поиск кухни")
    @ResponseStatus(HttpStatus.OK)
    CuisineDto findById(@PathVariable UUID id) throws CuisineException;

    @GetMapping("/cuisines")
    @Operation(operationId = "getAllCuisines", summary = "Получить все типы кухонь")
    @ResponseStatus(HttpStatus.OK)
    Page<CuisineDto> getAllCuisines(@ParameterObject Pageable pageable);

    @PutMapping("/{id}")
    @Operation(operationId = "updateCuisine", summary = "Обновить кухню")
    @ResponseStatus(HttpStatus.OK)
    CuisineDto updateCuisine(@PathVariable UUID id, @Valid @RequestBody CuisineRequest request) throws CuisineException;

    @DeleteMapping("/{id}")
    @Operation(operationId = "removeCuisine", summary = "Удалить кухню")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCuisine(@PathVariable UUID id);

}
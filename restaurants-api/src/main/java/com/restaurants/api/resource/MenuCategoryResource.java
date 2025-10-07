package com.restaurants.api.resource;

import com.restaurants.api.exception.MenuCategoryException;
import com.restaurants.api.modules.restaurant.dto.MenuCategoryDto;
import com.restaurants.api.modules.restaurant.request.MenuCategoryRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "MenuCategory", description = "Категория меню")
public interface MenuCategoryResource {

    @PutMapping("/{id}")
    @Operation(operationId = "updatedMenuCategoryUsingPut", summary = "Обновление категории меню")
    @ResponseStatus(HttpStatus.CREATED)
    MenuCategoryDto update(@PathVariable UUID id, @Valid @RequestBody MenuCategoryRequest request) throws MenuCategoryException;

    @GetMapping("/{id}")
    @Operation(operationId = "findById", summary = "Найти категорию по идентификатору")
    @ResponseStatus(HttpStatus.OK)
    MenuCategoryDto findById(@PathVariable UUID id) throws MenuCategoryException;

    @DeleteMapping("/{id}")
    @Operation(operationId = "deleteMenuCategoryUsingDelete", summary = "Удаление категории меню")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable UUID id) throws MenuCategoryException;

}

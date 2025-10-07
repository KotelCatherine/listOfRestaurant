package com.restaurants.api.resource;

import com.restaurants.api.exception.MenuItemException;
import com.restaurants.api.modules.restaurant.dto.MenuItemDto;
import com.restaurants.api.modules.restaurant.request.MenuItemRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "MenuItem", description = "Пункт меню")
public interface MenuItemResource {

    @PostMapping("/{menuCategoryId}")
    @Operation(operationId = "createMenuItemByMenuCategoryIdUsingPost", summary = "Создание пункта меню")
    @ResponseStatus(HttpStatus.CREATED)
    MenuItemDto create(@PathVariable UUID menuCategoryId, @RequestBody MenuItemRequest menuItemRequest);

    @GetMapping("/{id}")
    @Operation(operationId = "findMenuItemByIdUsingGet", summary = "Поиск пункта меню")
    @ResponseStatus(HttpStatus.OK)
    MenuItemDto findById(@PathVariable UUID id) throws MenuItemException;

    @PutMapping("/{id}")
    @Operation(operationId = "updateMenuItemByIdUsingPut", summary = "Обновление пункта меню")
    @ResponseStatus(HttpStatus.OK)
    MenuItemDto update(@PathVariable UUID id, @RequestBody MenuItemRequest request) throws MenuItemException;

    @DeleteMapping("/{id}")
    @Operation(operationId = "deleteMenuItemUsingDelete", summary = "Удаление пункта меню")
    @ResponseStatus(HttpStatus.OK)
    void delete(@PathVariable UUID id) throws MenuItemException;

}

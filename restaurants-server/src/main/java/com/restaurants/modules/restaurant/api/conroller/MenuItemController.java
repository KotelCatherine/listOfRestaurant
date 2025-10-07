package com.restaurants.modules.restaurant.api.conroller;

import com.restaurants.api.exception.MenuItemException;
import com.restaurants.api.modules.restaurant.dto.MenuItemDto;
import com.restaurants.api.modules.restaurant.request.MenuItemRequest;
import com.restaurants.api.resource.MenuItemResource;
import com.restaurants.modules.restaurant.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("menuItem")
@RequiredArgsConstructor
public class MenuItemController implements MenuItemResource {

    private final MenuItemService service;


    @Override
    public MenuItemDto create(UUID menuCategoryId, MenuItemRequest request) {
        return service.create(menuCategoryId, request);
    }

    @Override
    @GetMapping("/{id}")
    public MenuItemDto findById(@PathVariable UUID id) throws MenuItemException {
        return service.findById(id);
    }

    @Override
    @PutMapping("/{id}")
    public MenuItemDto update(@PathVariable UUID id, @RequestBody MenuItemRequest request) throws MenuItemException {
        return service.update(id, request);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) throws MenuItemException {
        service.delete(id);
    }

}

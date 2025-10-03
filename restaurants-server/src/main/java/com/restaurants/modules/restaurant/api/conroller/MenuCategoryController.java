package com.restaurants.modules.restaurant.api.conroller;

import com.restaurants.api.exception.MenuCategoryException;
import com.restaurants.api.modules.restaurant.dto.MenuCategoryDto;
import com.restaurants.api.modules.restaurant.request.MenuCategoryRequest;
import com.restaurants.api.resource.MenuCategoryResource;
import com.restaurants.modules.restaurant.service.MenuCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping(value = "menu-category")
@RestController
@RequiredArgsConstructor
public class MenuCategoryController implements MenuCategoryResource {

    private final MenuCategoryService service;

    @Override
    @PostMapping("/{id}")
    public MenuCategoryDto update(@PathVariable UUID id, @RequestBody MenuCategoryRequest request) throws MenuCategoryException {
        return service.update(id, request);
    }

    @Override
    @GetMapping("/{id}")
    public MenuCategoryDto findById(@PathVariable UUID id) throws MenuCategoryException {
        return service.findById(id);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) throws MenuCategoryException {
        service.delete(id);
    }

}

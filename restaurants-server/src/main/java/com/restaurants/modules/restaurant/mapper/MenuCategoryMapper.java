package com.restaurants.modules.restaurant.mapper;

import com.restaurants.api.modules.restaurant.dto.MenuCategoryDto;
import com.restaurants.api.modules.restaurant.request.MenuCategoryRequest;
import com.restaurants.modules.restaurant.entity.MenuCategory;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MenuCategoryMapper {

    public MenuCategory mapToEntity(UUID restaurantId, @Valid MenuCategoryRequest request) {
        return new MenuCategory()
                .id(UUID.randomUUID())
                .restaurantId(restaurantId)
                .name(request.name())
                .description(request.description());
    }

    public MenuCategoryDto mapToMenuCategoryDto(MenuCategory menuCategory) {
        return new MenuCategoryDto()
                .id(menuCategory.id())
                .restaurantId(menuCategory.restaurantId())
                .name(menuCategory.name())
                .description(menuCategory.description());
    }

    public MenuCategory mapToMenuCategory(MenuCategoryRequest request, MenuCategory menuCategory) {
        return menuCategory
                .name(request.name())
                .description(request.description());
    }

}

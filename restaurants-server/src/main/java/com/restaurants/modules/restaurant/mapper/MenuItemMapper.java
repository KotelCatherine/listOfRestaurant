package com.restaurants.modules.restaurant.mapper;

import com.restaurants.api.modules.restaurant.dto.MenuItemDto;
import com.restaurants.api.modules.restaurant.request.MenuItemRequest;
import com.restaurants.modules.restaurant.entity.MenuItem;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MenuItemMapper {

    public MenuItemDto mapToMenuItemDto(MenuItem menuItem) {
        return new MenuItemDto()
                .id(menuItem.id())
                .menuCategoryId(menuItem.menuCategoryId())
                .name(menuItem.name())
                .description(menuItem.description())
                .price(menuItem.price())
                .imageUrl(menuItem.imageUrl());
    }

    public MenuItem mapToEntity(MenuItem menuItem, MenuItemRequest request) {
        return menuItem.menuCategoryId(request.menuCategoryId())
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .imageUrl(request.imageUrl());
    }

    public MenuItem mapToMenuItem(UUID menuCategoryId, MenuItemRequest request) {
        return new MenuItem()
                .id(UUID.randomUUID())
                .menuCategoryId(menuCategoryId)
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .imageUrl(request.imageUrl());
    }

}

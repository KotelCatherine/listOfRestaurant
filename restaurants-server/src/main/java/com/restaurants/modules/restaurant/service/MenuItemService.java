package com.restaurants.modules.restaurant.service;

import com.restaurants.api.exception.MenuItemErrorCodeEnum;
import com.restaurants.api.exception.MenuItemException;
import com.restaurants.api.modules.restaurant.dto.MenuItemDto;
import com.restaurants.api.modules.restaurant.request.MenuItemRequest;
import com.restaurants.modules.restaurant.mapper.MenuItemMapper;
import com.restaurants.modules.restaurant.repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import com.restaurants.modules.restaurant.entity.MenuItem;

import java.util.UUID;

@Slf4j
@Validated
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MenuItemService {

    private final MenuItemRepository repository;
    private final MenuItemMapper mapper;

    @Transactional(rollbackFor = Exception.class)
    public MenuItemDto create(UUID menuCategoryId, MenuItemRequest request) {

        MenuItem menuItem = mapper.mapToMenuItem(menuCategoryId, request);

        repository.saveAndFlush(menuItem);

        return mapper.mapToMenuItemDto(menuItem);

    }

    public MenuItemDto findById(UUID id) throws MenuItemException {

        MenuItem menuItem = repository.findById(id)
                .orElseThrow(() -> new MenuItemException(MenuItemErrorCodeEnum.NOT_FOUND_MENU_ITEM_BY_ID));

        return mapper.mapToMenuItemDto(menuItem);

    }

    @Transactional(rollbackFor = Exception.class)
    public MenuItemDto update(UUID id, MenuItemRequest request) throws MenuItemException {

        MenuItem menuItem = repository.findById(id)
                .orElseThrow(() -> new MenuItemException(MenuItemErrorCodeEnum.NOT_FOUND_MENU_ITEM_BY_ID));

        menuItem = mapper.mapToEntity(menuItem, request);

        repository.saveAndFlush(menuItem);

        return mapper.mapToMenuItemDto(menuItem);

    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(UUID id) throws MenuItemException {

        if (!repository.existsById(id)) {
            throw new MenuItemException(MenuItemErrorCodeEnum.NOT_FOUND_MENU_ITEM_BY_ID);
        }

        repository.deleteById(id);

    }

}

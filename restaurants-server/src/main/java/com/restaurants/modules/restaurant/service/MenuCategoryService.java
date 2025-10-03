package com.restaurants.modules.restaurant.service;

import com.restaurants.api.exception.MenuCategoryErrorCodeEnum;
import com.restaurants.api.exception.MenuCategoryException;
import com.restaurants.api.modules.restaurant.dto.MenuCategoryDto;
import com.restaurants.api.modules.restaurant.request.MenuCategoryRequest;
import com.restaurants.modules.restaurant.entity.MenuCategory;
import com.restaurants.modules.restaurant.mapper.MenuCategoryMapper;
import com.restaurants.modules.restaurant.repository.MenuCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Slf4j
@Validated
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MenuCategoryService {

    private final MenuCategoryRepository menuCategoryRepository;
    private final MenuCategoryMapper mapper;

    @Transactional(rollbackFor = Exception.class)
    public MenuCategoryDto update(UUID id, MenuCategoryRequest request) throws MenuCategoryException {

        if(!menuCategoryRepository.existsById(id)) {
            throw new MenuCategoryException(MenuCategoryErrorCodeEnum.NOT_FOUND_CATEGORY_BY_ID);
        }
        MenuCategory menuCategory = menuCategoryRepository.findById(id)
                .orElseThrow();

        menuCategory = mapper.mapToMenuCategory(request, menuCategory);

        menuCategoryRepository.saveAndFlush(menuCategory);

        return mapper.mapToMenuCategoryDto(menuCategory);

    }


    public MenuCategoryDto findById(UUID id) throws MenuCategoryException {

        MenuCategory menuCategory = menuCategoryRepository.findById(id)
                .orElseThrow(() -> new MenuCategoryException(MenuCategoryErrorCodeEnum.NOT_FOUND_CATEGORY_BY_ID));

        return mapper.mapToMenuCategoryDto(menuCategory);

    }

    public void delete(UUID id) throws MenuCategoryException {

        if(!menuCategoryRepository.existsById(id)) {
            throw new MenuCategoryException(MenuCategoryErrorCodeEnum.NOT_FOUND_CATEGORY_BY_ID);
        }

        menuCategoryRepository.deleteById(id);

    }

}

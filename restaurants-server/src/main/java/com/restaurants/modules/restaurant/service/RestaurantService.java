package com.restaurants.modules.restaurant.service;

import com.restaurants.api.exception.CuisineErrorCodeEnum;
import com.restaurants.api.exception.CuisineException;
import com.restaurants.api.exception.RestaurantErrorCodeEnum;
import com.restaurants.api.exception.RestaurantException;
import com.restaurants.api.modules.restaurant.dto.AddressDto;
import com.restaurants.api.modules.restaurant.dto.CuisineDto;
import com.restaurants.api.modules.restaurant.dto.MenuCategoryDto;
import com.restaurants.api.modules.restaurant.dto.RestaurantDto;
import com.restaurants.api.modules.restaurant.request.AddressRequest;
import com.restaurants.api.modules.restaurant.request.CreateRestaurantRequest;
import com.restaurants.api.modules.restaurant.request.MenuCategoryRequest;
import com.restaurants.api.modules.restaurant.request.UpdateRestaurantRequest;
import com.restaurants.modules.restaurant.entity.*;
import com.restaurants.modules.restaurant.mapper.AddressMapper;
import com.restaurants.modules.restaurant.mapper.MenuCategoryMapper;
import com.restaurants.modules.restaurant.mapper.RestaurantMapper;
import com.restaurants.modules.restaurant.repository.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Validated
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantCuisinesRepository restaurantCuisinesRepository;
    private final CuisinesRepository cuisinesRepository;
    private final AddressRepository addressRepository;
    private final MenuCategoryRepository menuCategoryRepository;

    private final RestaurantMapper restaurantMapper;
    private final AddressMapper addressMapper;
    private final MenuCategoryMapper menuCategoryMapper;

    @Transactional(rollbackFor = Exception.class)
    public RestaurantDto create(@Valid CreateRestaurantRequest request) throws RestaurantException {

        checkName(request.name());
        Restaurant restaurant = restaurantMapper.mapToEntity(request);
        restaurantRepository.saveAndFlush(restaurant);

        return restaurantMapper.mapToRestaurantDto(restaurant);

    }

    public RestaurantDto findById(UUID id) throws RestaurantException {

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantException(RestaurantErrorCodeEnum.NOT_FOUND_RESTAURANT_BY_ID));

        return restaurantMapper.mapToRestaurantDto(restaurant);

    }

    @Transactional(rollbackFor = Exception.class)
    public RestaurantDto update(UUID id, @Valid UpdateRestaurantRequest request) throws RestaurantException {

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantException(RestaurantErrorCodeEnum.NOT_FOUND_RESTAURANT_BY_ID));

        Restaurant updatedRestaurant = restaurantMapper.mapToRestaurant(request, restaurant);

        restaurantRepository.saveAndFlush(restaurant);

        return restaurantMapper.mapToDto(updatedRestaurant);

    }

    @Transactional
    public void delete(UUID id) throws RestaurantException {

        extracted(id);

        restaurantRepository.deleteById(id);

    }

    @Transactional(readOnly = true)
    public Page<RestaurantDto> getAllRestaurants(Pageable pageable) {

        // ↓ 1. Гарантируем детерминированный порядок ‒ без него пагинация бессмысленна
        if (!pageable.getSort().isSorted()) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());   // Restaurant_ — метамодель JPA
        }

        // ↓ 2. Запрашиваем только нужную страницу
        Page<Restaurant> restaurants = restaurantRepository.findAll(pageable);

        // ↓ 3. Проецируем сущности в DTO через маппер (MapStruct / ModelMapper — не принципиально)
        return restaurants.map(restaurantMapper::mapToDto);    // restaurantMapper.toDto(Restaurant) → RestaurantDto

    }

    public List<RestaurantDto> searchRestaurantsByName(String nameQuery) {

        List<Restaurant> restaurants = restaurantRepository.findByName(nameQuery);
        List<RestaurantDto> restaurantsDto = new ArrayList<>();

        for (Restaurant restaurant : restaurants) {
            restaurantsDto.add(new RestaurantDto().name(restaurant.name()));
        }

        return restaurantsDto;

    }

    public List<CuisineDto> findAllCuisines(UUID restaurantId) throws RestaurantException {

        List<RestaurantCuisines> restaurantCuisines = restaurantCuisinesRepository.findAllByRestaurantId(restaurantId);

        return restaurantCuisines.stream()
                .map(rc -> cuisinesRepository.findById(rc.cuisineId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(restaurantMapper::mapToCuisineDto)
                .toList();

    }

    public List<RestaurantDto> findAllRestaurants(UUID cuisineId) throws RestaurantException {

        if (!cuisinesRepository.existsById(cuisineId)) {
            throw new RestaurantException(RestaurantErrorCodeEnum.NOT_FOUND_CUISINE_BY_ID);
        }

        List<RestaurantCuisines> allByCuisineId = restaurantCuisinesRepository.findAllByCuisineId(cuisineId);

        if (allByCuisineId.isEmpty()) {
            return List.of();
        }

        List<RestaurantDto> restaurants = new ArrayList<>();

        for (RestaurantCuisines restaurantCuisines : allByCuisineId) {

            UUID restaurantId = restaurantCuisines.restaurantId();
            Restaurant restaurant = restaurantRepository.findById(restaurantId)
                    .orElseThrow(() -> new RestaurantException(RestaurantErrorCodeEnum.NOT_FOUND_RESTAURANT_BY_ID));

            restaurants.add(restaurantMapper.mapToRestaurantDto(restaurant));

        }

        return restaurants;

    }

    @Transactional(rollbackFor = Exception.class)
    public CuisineDto createRestaurantCuisine(UUID restaurantId, UUID cuisineId) throws RestaurantException, CuisineException {

        extracted(restaurantId);

        Cuisine cuisine = cuisinesRepository.findById(cuisineId)
                .orElseThrow(() -> new CuisineException(CuisineErrorCodeEnum.NOT_FOUND_CUISINE_BY_ID));

        RestaurantCuisines restaurantCuisines = new RestaurantCuisines()
                .id(UUID.randomUUID())
                .restaurantId(restaurantId)
                .cuisineId(cuisineId);

        restaurantCuisinesRepository.saveAndFlush(restaurantCuisines);

        return restaurantMapper.mapToCuisineDto(cuisine);

    }

    @Transactional(rollbackFor = Exception.class)
    public AddressDto createAddress(UUID restaurantId, @Valid AddressRequest request) throws RestaurantException {

        extracted(restaurantId);

        Address address = addressMapper.mapToEntity(restaurantId, request);

        addressRepository.saveAndFlush(address);

        return addressMapper.mapToAddressDto(address);

    }

    @Transactional(rollbackFor = Exception.class)
    public MenuCategoryDto createMenuCategory(UUID restaurantId, @Valid MenuCategoryRequest request) throws RestaurantException {

        extracted(restaurantId);

        MenuCategory menuCategory = menuCategoryMapper.mapToEntity(restaurantId, request);

        menuCategoryRepository.saveAndFlush(menuCategory);

        return menuCategoryMapper.mapToMenuCategoryDto(menuCategory);

    }

    private void extracted(UUID restaurantId) throws RestaurantException {
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new RestaurantException(RestaurantErrorCodeEnum.NOT_FOUND_RESTAURANT_BY_ID);
        }
    }

    private void checkName(String name) throws RestaurantException {

        if (restaurantRepository.existsByNameIgnoreCase(name)) {
            throw new RestaurantException(RestaurantErrorCodeEnum.RESTAURANT_NAME_ALREADY_EXISTS);
        }

    }

}

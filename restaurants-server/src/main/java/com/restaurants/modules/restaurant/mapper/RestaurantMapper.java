package com.restaurants.modules.restaurant.mapper;

import com.restaurants.api.modules.restaurant.dto.CuisineDto;
import com.restaurants.api.modules.restaurant.dto.RestaurantDto;
import com.restaurants.api.modules.restaurant.request.RestaurantRequest;
import com.restaurants.modules.restaurant.entity.Cuisine;
import com.restaurants.modules.restaurant.entity.Restaurant;
import com.restaurants.modules.restaurant.entity.RestaurantCuisines;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RestaurantMapper {

    public Restaurant mapToRestaurant(RestaurantRequest request, Restaurant restaurant) {
        return restaurant
                .name(request.name())
                .description(request.description())
                .phone(request.phone())
                .email(request.email())
                .website(request.website())
                .status(request.status());
    }

    public Restaurant mapToEntity(RestaurantRequest request) {
        return new Restaurant()
                .id(UUID.randomUUID())
                .name(request.name())
                .description(request.description())
                .phone(request.phone())
                .email(request.email())
                .website(request.website())
                .status("ACTIVE");
    }

    public RestaurantDto mapToRestaurantDto(Restaurant restaurant) {
        return new RestaurantDto()
                .id(restaurant.id())
                .name(restaurant.name())
                .email(restaurant.email())
                .description(restaurant.description())
                .phone(restaurant.phone())
                .website(restaurant.website())
                .status(restaurant.status());
    }

    public CuisineDto mapToCuisineDto(Cuisine cuisine) {
        return new CuisineDto()
                .id(cuisine.id())
                .name(cuisine.name())
                .description(cuisine.description());
    }

    public static RestaurantCuisines mapToRestaurantCuisine(Cuisine cuisine, Restaurant restaurant) {
        return new RestaurantCuisines()
                .id(UUID.randomUUID())
                .cuisineId(cuisine.id())
                .restaurantId(restaurant.id());
    }

}

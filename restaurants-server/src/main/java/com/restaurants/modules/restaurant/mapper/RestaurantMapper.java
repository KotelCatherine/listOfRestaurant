package com.restaurants.modules.restaurant.mapper;

import com.restaurants.api.modules.restaurant.dto.FindRestaurantDto;
import com.restaurants.api.modules.restaurant.dto.RestaurantDto;
import com.restaurants.api.modules.restaurant.request.CreateRestaurantRequest;
import com.restaurants.api.modules.restaurant.request.UpdateRestaurantRequest;
import com.restaurants.modules.restaurant.entity.Restaurant;
import com.restaurants.modules.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RestaurantMapper {

    public RestaurantDto mapToDto(Restaurant restaurant) {
        return new RestaurantDto()
                .id(restaurant.id())
                .name(restaurant.name())
                .email(restaurant.email())
                .description(restaurant.description())
                .phone(restaurant.phone())
                .website(restaurant.website());
    }

    public Restaurant mapToRestaurant(UpdateRestaurantRequest request, Restaurant restaurant) {

        return restaurant
                .name(request.name())
                .description(request.description())
                .phone(request.phone())
                .email(request.email())
                .website(request.website());

    }

    public Restaurant mapToEntity(CreateRestaurantRequest request) {
        return new Restaurant()
                .id(UUID.randomUUID())
                .name(request.name())
                .description(request.description())
                .phone(request.phone())
                .email(request.email())
                .website(request.website())
                .versionId(BigInteger.ONE)
                .status("ACTIVE")
                .createdAt(LocalDateTime.now())
                .createdBy("system");
    }

    public FindRestaurantDto mapToFindRestaurant(Restaurant restaurant){
        return new FindRestaurantDto()
                .name(restaurant.name());

    }

}

package com.restaurants.modules.restaurant.repository;

import com.restaurants.modules.restaurant.entity.RestaurantCuisines;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RestaurantCuisinesRepository extends JpaRepository<RestaurantCuisines, UUID> {
    List<RestaurantCuisines> findAllByRestaurantId(UUID restaurantId);

    List<RestaurantCuisines> findAllByCuisineId(UUID cuisineId);
}

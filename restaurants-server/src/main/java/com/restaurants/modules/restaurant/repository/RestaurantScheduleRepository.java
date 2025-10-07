package com.restaurants.modules.restaurant.repository;

import com.restaurants.modules.restaurant.entity.RestaurantSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RestaurantScheduleRepository extends JpaRepository<RestaurantSchedule, UUID> {
}

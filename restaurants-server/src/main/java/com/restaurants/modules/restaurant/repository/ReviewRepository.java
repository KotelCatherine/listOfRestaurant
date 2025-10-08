package com.restaurants.modules.restaurant.repository;

import com.restaurants.modules.restaurant.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {

    List<Review> findAllByRestaurantId(UUID restaurantId);

    List<Review> findAllByUserId(UUID userId);

    boolean existsByUserId(UUID userId);

    boolean existsByRestaurantId(UUID restaurantId);

}

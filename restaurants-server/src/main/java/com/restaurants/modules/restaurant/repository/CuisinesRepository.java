package com.restaurants.modules.restaurant.repository;

import com.restaurants.modules.restaurant.entity.Cuisine;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CuisinesRepository extends JpaRepository<Cuisine, UUID> {
    boolean existsByNameIgnoreCase(@NotBlank String name);
}

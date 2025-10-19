package com.restaurants.modules.restaurant.repository;

import com.restaurants.modules.restaurant.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {

    @Query("SELECT a.restaurantId FROM addresses a WHERE a.city = :city")
    List<UUID> findAllRestaurantIdByCity(@Param("city") String city);

    @Query("SELECT DISTINCT a.city FROM addresses a ORDER BY a.city")
    List<String> findAllCities();

    Address findByRestaurantId(UUID restaurantId);

}

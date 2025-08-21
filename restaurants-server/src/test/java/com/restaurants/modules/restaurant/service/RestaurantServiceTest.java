package com.restaurants.modules.restaurant.service;


import com.restaurants.TestContainerInitialization;
import com.restaurants.api.exception.RestaurantErrorCodeEnum;
import com.restaurants.api.exception.RestaurantException;
import com.restaurants.api.modules.restaurant.dto.FindRestaurantDto;
import com.restaurants.api.modules.restaurant.dto.RestaurantDto;
import com.restaurants.api.modules.restaurant.request.CreateRestaurantRequest;
import com.restaurants.api.modules.restaurant.request.UpdateRestaurantRequest;
import com.restaurants.modules.restaurant.entity.Restaurant;
import com.restaurants.modules.restaurant.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;

//TODO: Дописать тесты
@SpringBootTest
public class RestaurantServiceTest extends TestContainerInitialization {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @AfterEach
    public void clean() {
        restaurantRepository.deleteAll();
    }

    @Test
    void findById_whenRestaurantExists_thenReturnRestaurant() {

        Restaurant restaurant = createRestaurant("Нямням");

        FindRestaurantDto result = Assertions.assertDoesNotThrow(() -> restaurantService.findById(restaurant.id()));

        Assertions.assertEquals(restaurant.name(), result.name());

    }

    @Test
    void findById_whenRestaurantDoesNotExist_thenReturnNull() {

        createRestaurant("Нямням");

        RestaurantException exception = Assertions.assertThrows(RestaurantException.class, () -> restaurantService.findById(UUID.randomUUID()));

        Assertions.assertEquals(RestaurantErrorCodeEnum.NOT_FOUND_RESTAURANT_BY_ID.errorCode(), exception.errorCode());

    }

    @Test
    void update_whenRestaurantDoesNotExist_thenThrow() {

        createRestaurant("FoodCore");

        RestaurantException exception = Assertions.assertThrows(RestaurantException.class, () -> restaurantService.update(
                UUID.randomUUID(), getUpdateRestaurantRequest()));

        Assertions.assertEquals(RestaurantErrorCodeEnum.NOT_FOUND_RESTAURANT_BY_ID.errorCode(), exception.errorCode());

    }

    @Test
    @Transactional
    void update_whenRestaurantExist_thenUpdate() {

        Restaurant restaurant = createRestaurant("FoodCore");
        UUID id = restaurant.id();

        UpdateRestaurantRequest request = getUpdateRestaurantRequest();

        RestaurantDto result = Assertions.assertDoesNotThrow(() -> restaurantService.update(id, request));

        Assertions.assertEquals("Food", result.name());

    }

    private UpdateRestaurantRequest getUpdateRestaurantRequest() {
        return new UpdateRestaurantRequest(
                "Food",
                "Korean food",
                "+79509809966",
                "food@gmail.yam",
                "http//:foodcore.ru");
    }

    private Restaurant createRestaurant(String name) {

        Restaurant restaurant = new Restaurant()
                .id(UUID.randomUUID())
                .versionId(BigInteger.valueOf(1))
                .name(name)
                .status("Active")
                .createdAt(LocalDateTime.now())
                .createdBy("admin");

        restaurant = restaurantRepository.saveAndFlush(restaurant);

        return restaurant;

    }

    @Test
    void create_whenRestaurantDoesNotExist_thenCreate() throws RestaurantException {

        CreateRestaurantRequest request = new CreateRestaurantRequest();
        request.name("FoodCore");

        restaurantService.create(request);

        Assertions.assertTrue(restaurantRepository.existsByName("FoodCore"));

    }

}
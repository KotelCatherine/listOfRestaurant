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
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootTest
public class RestaurantServiceTest extends TestContainerInitialization {

    private static final String DEFAULT_RESTAURANT_NAME = "naym";
    private static final String DEFAULT_VALID_PHONE_NUMBER = "+79333258846";
    private static final String DEFAULT_VALID_EMAIL = "ya@gu.ru";

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @AfterEach
    public void clean() {
        restaurantRepository.deleteAll();
    }

    @Test
    void create_whenRestaurantNameExist_thenThrow() {

        createRestaurant(DEFAULT_RESTAURANT_NAME);
        CreateRestaurantRequest request = getCreateRestaurantRequest(DEFAULT_RESTAURANT_NAME, null, null);

        RestaurantException exception = Assertions.assertThrows(RestaurantException.class, () -> restaurantService.create(request));
        Assertions.assertEquals(RestaurantErrorCodeEnum.RESTAURANT_NAME_ALREADY_EXISTS.errorCode(), exception.errorCode());

    }

    @ParameterizedTest
    @MethodSource("createValidData")
    @Transactional
    void create_whenEmailAndPhoneValid_thenCreate(String phone, String email) {

        CreateRestaurantRequest request = getCreateRestaurantRequest(DEFAULT_RESTAURANT_NAME, phone, email);

        Assertions.assertDoesNotThrow(() -> restaurantService.create(request));

    }

    @ParameterizedTest
    @MethodSource("createInvalidData")
    @Transactional
    void create_whenEmailAndPhoneInvalid_thenThrow(String phone, String email) {

        CreateRestaurantRequest request = getCreateRestaurantRequest(DEFAULT_RESTAURANT_NAME, phone, email);

        Assertions.assertThrows(ConstraintViolationException.class, () -> restaurantService.create(request));

    }

    @Test
    @Transactional
    void create_whenRestaurantNameNotExist_thenCreate() {

        createRestaurant(DEFAULT_RESTAURANT_NAME);
        CreateRestaurantRequest request = getCreateRestaurantRequest("naumnaum", null, null);

        RestaurantDto result = Assertions.assertDoesNotThrow(() -> restaurantService.create(request));

        Assertions.assertEquals(request.name(), result.name());

    }

    @Test
    void findById_whenRestaurantExists_thenReturnRestaurant() {

        Restaurant restaurant = createRestaurant(DEFAULT_RESTAURANT_NAME);

        FindRestaurantDto result = Assertions.assertDoesNotThrow(() -> restaurantService.findById(restaurant.id()));

        Assertions.assertEquals(restaurant.name(), result.name());

    }

    @Test
    void findById_whenRestaurantDoesNotExist_thenReturnNull() {

        createRestaurant(DEFAULT_RESTAURANT_NAME);

        RestaurantException exception = Assertions.assertThrows(RestaurantException.class, () -> restaurantService.findById(UUID.randomUUID()));

        Assertions.assertEquals(RestaurantErrorCodeEnum.NOT_FOUND_RESTAURANT_BY_ID.errorCode(), exception.errorCode());

    }

    @Test
    void update_whenRestaurantDoesNotExist_thenThrow() {

        createRestaurant(DEFAULT_RESTAURANT_NAME);

        RestaurantException exception = Assertions.assertThrows(RestaurantException.class, () -> restaurantService.update(
                UUID.randomUUID(), getUpdateRestaurantRequest(null, null)));

        Assertions.assertEquals(RestaurantErrorCodeEnum.NOT_FOUND_RESTAURANT_BY_ID.errorCode(), exception.errorCode());

    }

    @Test
    @Transactional
    void update_whenRestaurantExist_thenUpdate() {

        Restaurant restaurant = createRestaurant(DEFAULT_RESTAURANT_NAME);
        UUID id = restaurant.id();

        UpdateRestaurantRequest request = getUpdateRestaurantRequest(null, null);

        RestaurantDto result = Assertions.assertDoesNotThrow(() -> restaurantService.update(id, request));

        Assertions.assertEquals(DEFAULT_RESTAURANT_NAME, result.name());

    }

    @ParameterizedTest
    @MethodSource("createValidData")
    @Transactional
    void update_whenPhoneAndEmailValid_thenUpdate(String phone, String email) {

        Restaurant restaurant = createRestaurant(DEFAULT_RESTAURANT_NAME);
        UpdateRestaurantRequest request = getUpdateRestaurantRequest(phone, email);
        UUID id = restaurant.id();

        RestaurantDto result = Assertions.assertDoesNotThrow(() -> restaurantService.update(id, request));

        Assertions.assertEquals(phone, result.phone());
        Assertions.assertEquals(email, result.email());

    }

    @ParameterizedTest
    @MethodSource("createInvalidData")
    void update_whenPhoneAndEmailInvalid_thenThrow(String phone, String email) {

        Restaurant restaurant = createRestaurant(DEFAULT_RESTAURANT_NAME);
        UpdateRestaurantRequest request = getUpdateRestaurantRequest(phone, email);
        UUID id = restaurant.id();

        Assertions.assertThrows(ConstraintViolationException.class, () -> restaurantService.update(id, request));

    }

    @Test
    void delete_whenRestaurantDoNotFind_thenThrow() {

        createRestaurant(DEFAULT_RESTAURANT_NAME);
        UUID id = UUID.randomUUID();
        RestaurantException exception = Assertions.assertThrows(RestaurantException.class, () -> restaurantService.delete(id));

        Assertions.assertEquals(RestaurantErrorCodeEnum.NOT_FOUND_RESTAURANT_BY_ID.errorCode(), exception.errorCode());

    }

    @Test
    void delete_whenRestaurantDoNotFind_thenDelete() {

        Restaurant restaurant = createRestaurant(DEFAULT_RESTAURANT_NAME);
        UUID id = restaurant.id();

        Assertions.assertTrue(restaurantRepository.existsById(id));
        Assertions.assertDoesNotThrow(() -> restaurantService.delete(id));
        Assertions.assertFalse(restaurantRepository.existsById(id));

    }


    private CreateRestaurantRequest getCreateRestaurantRequest(String name, String phone, String email) {
        return new CreateRestaurantRequest()
                .name(name)
                .phone(phone)
                .email(email);
    }

    private UpdateRestaurantRequest getUpdateRestaurantRequest(String phone, String email) {
        return new UpdateRestaurantRequest()
                .name(DEFAULT_RESTAURANT_NAME)
                .phone(phone)
                .email(email);
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

    private Stream<Arguments> createValidData() {
        return Stream.of(
                Arguments.of(DEFAULT_VALID_PHONE_NUMBER, DEFAULT_VALID_EMAIL),
                Arguments.of(null, DEFAULT_VALID_EMAIL),
                Arguments.of(DEFAULT_VALID_PHONE_NUMBER, null),
                Arguments.of(DEFAULT_VALID_PHONE_NUMBER, "ya@gu.com"),
                Arguments.of("89333258846", "ya@gu.com")
        );
    }

    private Stream<Arguments> createInvalidData() {
        return Stream.of(
                Arguments.of(DEFAULT_VALID_PHONE_NUMBER, "yagu.ru"),
                Arguments.of("+7933325884", DEFAULT_VALID_EMAIL),
                Arguments.of("8933325884", DEFAULT_VALID_EMAIL),
                Arguments.of("79333258846", DEFAULT_VALID_EMAIL),
                Arguments.of(DEFAULT_VALID_PHONE_NUMBER, "ya@guru")
        );
    }
}
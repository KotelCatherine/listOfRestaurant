package com.restaurants.modules.restaurant.service;

import com.restaurants.TestContainerInitialization;
import com.restaurants.api.exception.RestaurantErrorCodeEnum;
import com.restaurants.api.exception.RestaurantException;
import com.restaurants.api.modules.restaurant.dto.CuisineDto;
import com.restaurants.api.modules.restaurant.dto.FindRestaurantDto;
import com.restaurants.api.modules.restaurant.dto.RestaurantDto;
import com.restaurants.api.modules.restaurant.request.CreateRestaurantRequest;
import com.restaurants.api.modules.restaurant.request.UpdateRestaurantRequest;
import com.restaurants.modules.restaurant.entity.Cuisine;
import com.restaurants.modules.restaurant.entity.Restaurant;
import com.restaurants.modules.restaurant.entity.RestaurantCuisines;
import com.restaurants.modules.restaurant.repository.CuisinesRepository;
import com.restaurants.modules.restaurant.repository.RestaurantCuisinesRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootTest
public class RestaurantServiceTest extends TestContainerInitialization {

    private static final String DEFAULT_RESTAURANT_NAME = "naym";
    private static final String DEFAULT_VALID_PHONE_NUMBER = "+79333258846";
    private static final String DEFAULT_VALID_EMAIL = "ya@gu.ru";

    @Autowired
    private RestaurantService service;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CuisinesRepository cuisinesRepository;

    @Autowired
    private RestaurantCuisinesRepository restaurantCuisinesRepository;

    @AfterEach
    public void clean() {
        restaurantRepository.deleteAll();
    }

    @Test
    void create_whenRestaurantDoesNotExist_thenCreate() throws RestaurantException {

        CreateRestaurantRequest request = new CreateRestaurantRequest();
        request.name("FoodCore");

        service.create(request);

        Assertions.assertTrue(restaurantRepository.existsByName("FoodCore"));

    }

    @Test
    void create_whenRestaurantNameExist_thenThrow() {

        createRestaurant(DEFAULT_RESTAURANT_NAME);
        CreateRestaurantRequest request = getCreateRestaurantRequest(DEFAULT_RESTAURANT_NAME, null, null);

        RestaurantException exception = Assertions.assertThrows(RestaurantException.class, () -> service.create(request));
        Assertions.assertEquals(RestaurantErrorCodeEnum.RESTAURANT_NAME_ALREADY_EXISTS.errorCode(), exception.errorCode());

    }

    @ParameterizedTest
    @MethodSource("createValidData")
    @Transactional
    void create_whenEmailAndPhoneValid_thenCreate(String phone, String email) {

        CreateRestaurantRequest request = getCreateRestaurantRequest(DEFAULT_RESTAURANT_NAME, phone, email);

        Assertions.assertDoesNotThrow(() -> service.create(request));

    }

    @ParameterizedTest
    @MethodSource("createInvalidData")
    @Transactional
    void create_whenEmailAndPhoneInvalid_thenThrow(String phone, String email) {

        CreateRestaurantRequest request = getCreateRestaurantRequest(DEFAULT_RESTAURANT_NAME, phone, email);

        Assertions.assertThrows(ConstraintViolationException.class, () -> service.create(request));

    }

    @Test
    @Transactional
    void create_whenRestaurantNameNotExist_thenCreate() {

        createRestaurant(DEFAULT_RESTAURANT_NAME);
        CreateRestaurantRequest request = getCreateRestaurantRequest("naumnaum", null, null);

        RestaurantDto result = Assertions.assertDoesNotThrow(() -> service.create(request));

        Assertions.assertEquals(request.name(), result.name());

    }

    @Test
    void findById_whenRestaurantExists_thenReturnRestaurant() {

        Restaurant restaurant = createRestaurant(DEFAULT_RESTAURANT_NAME);

        FindRestaurantDto result = Assertions.assertDoesNotThrow(() -> service.findById(restaurant.id()));

        Assertions.assertEquals(restaurant.name(), result.name());

    }

    @Test
    void findById_whenRestaurantDoesNotExist_thenReturnNull() {

        createRestaurant(DEFAULT_RESTAURANT_NAME);

        RestaurantException exception = Assertions.assertThrows(RestaurantException.class, () -> service.findById(UUID.randomUUID()));

        Assertions.assertEquals(RestaurantErrorCodeEnum.NOT_FOUND_RESTAURANT_BY_ID.errorCode(), exception.errorCode());

    }

    @Test
    void update_whenRestaurantDoesNotExist_thenThrow() {

        createRestaurant(DEFAULT_RESTAURANT_NAME);

        RestaurantException exception = Assertions.assertThrows(RestaurantException.class, () -> service.update(
                UUID.randomUUID(), getUpdateRestaurantRequest(null, null)));

        Assertions.assertEquals(RestaurantErrorCodeEnum.NOT_FOUND_RESTAURANT_BY_ID.errorCode(), exception.errorCode());

    }

    @Test
    @Transactional
    void update_whenRestaurantExist_thenUpdate() {

        Restaurant restaurant = createRestaurant(DEFAULT_RESTAURANT_NAME);
        UUID id = restaurant.id();

        UpdateRestaurantRequest request = getUpdateRestaurantRequest(null, null);

        RestaurantDto result = Assertions.assertDoesNotThrow(() -> service.update(id, request));

        Assertions.assertEquals(DEFAULT_RESTAURANT_NAME, result.name());

    }

    @ParameterizedTest
    @MethodSource("createValidData")
    @Transactional
    void update_whenPhoneAndEmailValid_thenUpdate(String phone, String email) {

        Restaurant restaurant = createRestaurant(DEFAULT_RESTAURANT_NAME);
        UpdateRestaurantRequest request = getUpdateRestaurantRequest(phone, email);
        UUID id = restaurant.id();

        RestaurantDto result = Assertions.assertDoesNotThrow(() -> service.update(id, request));

        Assertions.assertEquals(phone, result.phone());
        Assertions.assertEquals(email, result.email());

    }

    @ParameterizedTest
    @MethodSource("createInvalidData")
    void update_whenPhoneAndEmailInvalid_thenThrow(String phone, String email) {

        Restaurant restaurant = createRestaurant(DEFAULT_RESTAURANT_NAME);
        UpdateRestaurantRequest request = getUpdateRestaurantRequest(phone, email);
        UUID id = restaurant.id();

        Assertions.assertThrows(ConstraintViolationException.class, () -> service.update(id, request));

    }

    @Test
    void delete_whenRestaurantDoNotFind_thenThrow() {

        createRestaurant(DEFAULT_RESTAURANT_NAME);
        UUID id = UUID.randomUUID();
        RestaurantException exception = Assertions.assertThrows(RestaurantException.class, () -> service.delete(id));

        Assertions.assertEquals(RestaurantErrorCodeEnum.NOT_FOUND_RESTAURANT_BY_ID.errorCode(), exception.errorCode());

    }

    @Test
    void delete_whenRestaurantFind_thenDelete() {

        Restaurant restaurant = createRestaurant(DEFAULT_RESTAURANT_NAME);
        UUID id = restaurant.id();

        Assertions.assertTrue(restaurantRepository.existsById(id));
        Assertions.assertDoesNotThrow(() -> service.delete(id));
        Assertions.assertFalse(restaurantRepository.existsById(id));

    }


    @Test
    void getAllRestaurants_whenRestaurantsNotExist_thenEmptyPage() {

        Pageable pageable = PageRequest.of(0, 5);

        Assertions.assertTrue(service.getAllRestaurants(pageable).isEmpty());

    }

    @Test
    void getAllRestaurants_whenRestaurantsExist_thenReturn() {

        Restaurant firstRestaurant = createRestaurant("Rest #1");
        Restaurant secondRestaurant = createRestaurant("Rest #2");
        Restaurant thirdRestaurant = createRestaurant("Rest #3");
        createRestaurant("Rest #4");
        createRestaurant("Rest #5");

        Pageable pageable = PageRequest.of(0, 3);

        Page<RestaurantDto> allRestaurants = service.getAllRestaurants(pageable);

        Assertions.assertEquals(5, allRestaurants.getTotalElements());
        Assertions.assertEquals(2, allRestaurants.getTotalPages());
        Assertions.assertEquals(firstRestaurant.name(), allRestaurants.getContent().get(0).name());
        Assertions.assertEquals(secondRestaurant.name(), allRestaurants.getContent().get(1).name());
        Assertions.assertEquals(thirdRestaurant.name(), allRestaurants.getContent().get(2).name());
        Assertions.assertEquals(3, allRestaurants.getContent().size());

    }

    @Test
    void getAllRestaurants_whenRestaurantsExistAnSortIsSet_thenReturn() {

        createRestaurant("Rest #5");
        Restaurant firstRestaurant = createRestaurant("Rest #1");
        createRestaurant("Rest #4");
        Restaurant thirdRestaurant = createRestaurant("Rest #3");
        Restaurant secondRestaurant = createRestaurant("Rest #2");

        Pageable pageable = PageRequest.of(0, 3, Sort.by("name").ascending());

        Page<RestaurantDto> allRestaurants = service.getAllRestaurants(pageable);

        Assertions.assertEquals(5, allRestaurants.getTotalElements());
        Assertions.assertEquals(2, allRestaurants.getTotalPages());
        Assertions.assertEquals(firstRestaurant.name(), allRestaurants.getContent().get(0).name());
        Assertions.assertEquals(secondRestaurant.name(), allRestaurants.getContent().get(1).name());
        Assertions.assertEquals(thirdRestaurant.name(), allRestaurants.getContent().get(2).name());
        Assertions.assertEquals(3, allRestaurants.getContent().size());

    }

    @Test
    void searchRestaurantsByName_whenRestaurantsNotFindByName_thenEmptyList() {

        createRestaurant("Rest #5");

        Assertions.assertTrue(service.searchRestaurantsByName("some").isEmpty());

    }

    @Test
    void searchRestaurantsByName_whenRestaurantsFindByName_thenReturn() {

        Restaurant firstRestaurant = createRestaurant("Rest #1");
        Restaurant secondRestaurant = createRestaurant("Rest #2");
        Restaurant thirdRestaurant = createRestaurant("Rest #3");

        List<RestaurantDto> restaurants = service.searchRestaurantsByName("Rest");

        Assertions.assertEquals(3, restaurants.size());
        Assertions.assertEquals(firstRestaurant.name(), restaurants.get(0).name());
        Assertions.assertEquals(secondRestaurant.name(), restaurants.get(1).name());
        Assertions.assertEquals(thirdRestaurant.name(), restaurants.get(2).name());

    }

    @Test
    void findAllCuisines_whenIdInvalid_thenThrow() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.findAllCuisines(UUID.fromString("123")));
    }

    @Test
    void findAllCuisines_whenRestaurantIsNotInTableYet_thenEmptyList() {

        Restaurant restaurant = createRestaurant(DEFAULT_RESTAURANT_NAME);

        Assertions.assertDoesNotThrow(() -> service.findAllCuisines(restaurant.id()).isEmpty());

    }

    @Test
    void findAllCuisines_whenCuisineByIdNotExist_thenThrow() {

        Restaurant restaurant = createRestaurant(DEFAULT_RESTAURANT_NAME);
        createRestaurantCuisines(restaurant.id(), UUID.randomUUID());

        Assertions.assertThrows(RestaurantException.class, () -> service.findAllCuisines(restaurant.id()));

    }

    @Test
    void findAllCuisines_whenRestaurantInTableYet_thenReturn() {

        Restaurant restaurant = createRestaurant(DEFAULT_RESTAURANT_NAME);
        Cuisine cuisine = createCuisines("Русская", "Щи да каша - пища наша");
        createRestaurantCuisines(restaurant.id(), cuisine.id());

        List<CuisineDto> allCuisines = Assertions.assertDoesNotThrow(() -> service.findAllCuisines(restaurant.id()));

        Assertions.assertEquals(cuisine.name(), allCuisines.get(0).name());

    }

    @Test
    void findAllRestaurants_whenCuisineIsNotExist_thenThrow() {

        Restaurant restaurant = createRestaurant(DEFAULT_RESTAURANT_NAME);
        Cuisine cuisine = createCuisines("Русская", "Щи да каша - пища наша");
        createRestaurantCuisines(restaurant.id(), cuisine.id());

        Assertions.assertThrows(RestaurantException.class, () -> service.findAllRestaurants(UUID.randomUUID()));

    }

    @Test
    void findAllRestaurants_whenIdInvalid_thenThrow() {

        Restaurant restaurant = createRestaurant(DEFAULT_RESTAURANT_NAME);
        Cuisine cuisine = createCuisines("Русская", "Щи да каша - пища наша");
        createRestaurantCuisines(restaurant.id(), cuisine.id());

        Assertions.assertThrows(IllegalArgumentException.class, () -> service.findAllRestaurants(UUID.fromString("123")));

    }

    @Test
    void findAllRestaurants_whenCuisineIsNotInTableYet_thenEmptyList() {

        Restaurant restaurant = createRestaurant(DEFAULT_RESTAURANT_NAME);
        Cuisine firstCuisine = createCuisines("Русская", "Щи да каша - пища наша");
        Cuisine secondCuisine = createCuisines("Русская", "Щи да каша - пища наша");
        createRestaurantCuisines(restaurant.id(), firstCuisine.id());


        List<RestaurantDto> allRestaurants = Assertions.assertDoesNotThrow(() -> service.findAllRestaurants(secondCuisine.id()));
        Assertions.assertTrue(allRestaurants.isEmpty());

    }

    @Test
    void findAllRestaurants_whenRestaurantNotExist_thenThrow() {

        createRestaurant(DEFAULT_RESTAURANT_NAME);
        Cuisine cuisine = createCuisines("Русская", "Щи да каша - пища наша");
        createRestaurantCuisines(UUID.randomUUID(), cuisine.id());

        Assertions.assertThrows(RestaurantException.class, () -> service.findAllRestaurants(cuisine.id()));

    }

    @Test
    void findAllRestaurants_whenCuisineInTableYet_thenReturn() {

        Restaurant restaurant = createRestaurant(DEFAULT_RESTAURANT_NAME);
        Cuisine cuisine = createCuisines("Русская", "Щи да каша - пища наша");
        createRestaurantCuisines(restaurant.id(), cuisine.id());


        List<RestaurantDto> allRestaurants = Assertions.assertDoesNotThrow(() -> service.findAllRestaurants(cuisine.id()));
        Assertions.assertEquals(restaurant.name(), allRestaurants.get(0).name());

    }


    private RestaurantCuisines createRestaurantCuisines(UUID restaurantId, UUID cuisineId) {

        RestaurantCuisines restaurantCuisines = new RestaurantCuisines()
                .id(UUID.randomUUID())
                .restaurantId(restaurantId)
                .cuisineId(cuisineId);

        restaurantCuisinesRepository.saveAndFlush(restaurantCuisines);

        return restaurantCuisines;

    }

    private Cuisine createCuisines(String name, String description) {

        Cuisine cuisine = new Cuisine()
                .id(UUID.randomUUID())
                .versionId(BigInteger.ONE)
                .name(name)
                .description(description);

        cuisinesRepository.saveAndFlush(cuisine);

        return cuisine;
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

package com.restaurants.modules.restaurant.service;

import com.restaurants.TestContainerInitialization;
import com.restaurants.api.exception.CuisineErrorCodeEnum;
import com.restaurants.api.exception.CuisineException;
import com.restaurants.api.modules.restaurant.request.CreateCuisineRequest;
import com.restaurants.modules.restaurant.entity.Cuisine;
import com.restaurants.modules.restaurant.repository.CuisinesRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.util.UUID;

@SpringBootTest
class CuisineServiceTest extends TestContainerInitialization {

    public static final String DEFAULT_NAME_CUISINE = "Русская";
    @Autowired
    private CuisineService service;

    @Autowired
    CuisinesRepository repository;

    @AfterEach
    void clear() {
        repository.deleteAll();
    }

    @Test
    void createCuisine_whenCuisineNameExist_thenThrow() {

        createCuisine(DEFAULT_NAME_CUISINE);

        CreateCuisineRequest request = getCreateCuisineRequest(DEFAULT_NAME_CUISINE);
        CuisineException exception = Assertions.assertThrows(CuisineException.class, () -> service.createCuisine(request));
        Assertions.assertEquals(CuisineErrorCodeEnum.CUISINE_NAME_ALREADY_EXISTS.errorCode(), exception.errorCode());

    }

    @Test
    void createCuisine_whenCuisineNameNotExist_thenReturn() {

        createCuisine(DEFAULT_NAME_CUISINE);

        CreateCuisineRequest request = getCreateCuisineRequest("Белорусская");

        Assertions.assertDoesNotThrow(() -> service.createCuisine(request));


    }

    private CreateCuisineRequest getCreateCuisineRequest(String name) {
        return new CreateCuisineRequest()
                .name(name);
    }

    private Cuisine createCuisine(String name) {

        Cuisine cuisine = new Cuisine()
                .id(UUID.randomUUID())
                .versionId(BigInteger.ONE)
                .name(name);

        cuisine = repository.saveAndFlush(cuisine);

        return cuisine;

    }


}
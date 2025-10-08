package com.restaurants.api.resource;

import com.restaurants.api.exception.ReviewException;
import com.restaurants.api.modules.restaurant.dto.ReviewDto;
import com.restaurants.api.modules.restaurant.request.ReviewRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Review", description = "Отзыв")
public interface ReviewResource {

    @PostMapping
    @Operation(operationId = "createReviewUsingPost", summary = "Создание нового отзыва")
    @ResponseStatus(HttpStatus.CREATED)
    ReviewDto create(@RequestBody ReviewRequest request);

    @GetMapping("/{id}")
    @Operation(operationId = "findReviewByIdUsingGet", summary = "Поиск отзыва по идентификатору")
    @ResponseStatus(HttpStatus.OK)
    ReviewDto findReviewById(@PathVariable UUID id) throws ReviewException;

    @GetMapping("/{restaurantId}/reviewsRestaurant")
    @Operation(operationId = "findAllReviewsRestaurantByRestaurantIdUsingGet", summary = "Поиск всех отзывов ресторана")
    @ResponseStatus(HttpStatus.OK)
    List<ReviewDto> findAllReviewsRestaurantByRestaurantId(@PathVariable UUID restaurantId) throws ReviewException;

    @GetMapping("/{userId}/reviewsUser")
    @Operation(operationId = "findAllReviewsUserByUserIdUsingGet", summary = "Поиск всех отзывов пользователя")
    @ResponseStatus(HttpStatus.OK)
    List<ReviewDto> findAllReviewsUserByUserId(@PathVariable UUID userId) throws ReviewException;

    @PutMapping("/{id}")
    @Operation(operationId = "updateReviewUsingPut", summary = "Обновление отзыва")
    @ResponseStatus(HttpStatus.OK)
    ReviewDto update(@PathVariable UUID id, @RequestBody ReviewRequest request) throws ReviewException;

    @DeleteMapping("/{id}")
    @Operation(operationId = "deleteReviewUsingDelete", summary = "Удаление отзыва")
    @ResponseStatus(HttpStatus.OK)
    void delete(@PathVariable UUID id) throws ReviewException;

}

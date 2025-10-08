package com.restaurants.modules.restaurant.api.conroller;

import com.restaurants.api.exception.ReviewException;
import com.restaurants.api.modules.restaurant.dto.ReviewDto;
import com.restaurants.api.modules.restaurant.request.ReviewRequest;
import com.restaurants.api.resource.ReviewResource;
import com.restaurants.modules.restaurant.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping(value = "/review")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewController implements ReviewResource{

    private final ReviewService service;

    @Override
    @PostMapping
    public ReviewDto create(@RequestBody ReviewRequest request) {
        return service.create(request);
    }

    @Override
    @GetMapping("/{id}")
    public ReviewDto findReviewById(@PathVariable UUID id) throws ReviewException {
        return service.findReviewById(id);
    }

    @Override
    @GetMapping("/{restaurantId}/reviewsRestaurant")
    public List<ReviewDto> findAllReviewsRestaurantByRestaurantId(@PathVariable UUID restaurantId) throws ReviewException {
        return service.findAllReviewsRestaurantByRestaurantId(restaurantId);
    }

    @Override
    @GetMapping("/{userId}/reviewsUser")
    public List<ReviewDto> findAllReviewsUserByUserId(@PathVariable UUID userId) throws ReviewException {
        return service.findAllReviewsUserByUserId(userId);
    }

    @Override
    @PutMapping("/{id}")
    public ReviewDto update(@PathVariable UUID id, @RequestBody ReviewRequest request) throws ReviewException {
        return service.update(id, request);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) throws ReviewException {
        service.delete(id);
    }

}

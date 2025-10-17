package com.restaurants.modules.restaurant.service;

import com.restaurants.api.exception.ReviewErrorCodeEnum;
import com.restaurants.api.exception.ReviewException;
import com.restaurants.api.modules.restaurant.dto.ReviewDto;
import com.restaurants.api.modules.restaurant.request.ReviewRequest;
import com.restaurants.modules.restaurant.entity.Review;
import com.restaurants.modules.restaurant.mapper.ReviewMapper;
import com.restaurants.modules.restaurant.repository.ReviewRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.OptionalDouble;
import java.util.UUID;

@Slf4j
@Validated
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository repository;
    private final ReviewMapper mapper;

    @Transactional(rollbackFor = Exception.class)
    public ReviewDto create(@Valid ReviewRequest request) {

        Review review = mapper.mapToReview(request);

        repository.saveAndFlush(review);

        return mapper.mapToReviewDto(review);

    }

    public ReviewDto findReviewById(UUID id) throws ReviewException {

        Review review = repository.findById(id)
                .orElseThrow(() -> new ReviewException(ReviewErrorCodeEnum.NOT_FOUND_REVIEW_BY_ID));

        return mapper.mapToReviewDto(review);

    }

    public List<ReviewDto> findAllReviewsRestaurantByRestaurantId(UUID restaurantId) throws ReviewException {

        if (!repository.existsByRestaurantId(restaurantId)) {
            throw new ReviewException(ReviewErrorCodeEnum.NOT_FOUND_USER_BY_ID);
        }

        List<Review> allByRestaurantId = repository.findAllByRestaurantId(restaurantId);

        return allByRestaurantId.stream()
                .map(mapper::mapToReviewDto)
                .toList();

    }


    public List<ReviewDto> findAllReviewsUserByUserId(UUID userId) throws ReviewException {

        if (!repository.existsByUserId(userId)) {
            throw new ReviewException(ReviewErrorCodeEnum.NOT_FOUND_USER_BY_ID);
        }

        List<Review> allByUserId = repository.findAllByUserId(userId);

        return allByUserId.stream()
                .map(mapper::mapToReviewDto)
                .toList();


    }

    @Transactional(rollbackFor = Exception.class)
    public ReviewDto update(UUID id, @Valid ReviewRequest request) throws ReviewException {

        Review review = repository.findById(id)
                .orElseThrow(() -> new ReviewException(ReviewErrorCodeEnum.NOT_FOUND_REVIEW_BY_ID));

        Review reviewUpdated = mapper.mapToUpdateReview(review, request);

        repository.saveAndFlush(reviewUpdated);

        return mapper.mapToReviewDto(reviewUpdated);

    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(UUID id) throws ReviewException {

        if (!repository.existsById(id)) {
            throw new ReviewException(ReviewErrorCodeEnum.NOT_FOUND_REVIEW_BY_ID);
        }

        repository.deleteById(id);

    }

    public int averageRatingReview(UUID restaurantId) throws ReviewException {

        List<Review> reviews = repository.findAllByRestaurantId(restaurantId);

        double average = reviews.stream()
                .mapToDouble(Review::rating)
                .average()
                .orElse(0.0);

        return (int) Math.round(average);

    }

}

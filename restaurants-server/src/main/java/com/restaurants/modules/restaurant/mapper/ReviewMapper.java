package com.restaurants.modules.restaurant.mapper;

import com.restaurants.api.modules.restaurant.dto.ReviewDto;
import com.restaurants.api.modules.restaurant.request.ReviewRequest;
import com.restaurants.modules.restaurant.entity.Review;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ReviewMapper {

    public Review mapToReview(ReviewRequest request) {
        return new Review()
                .id(UUID.randomUUID())
                .restaurantId(request.restaurantId())
                .userId(request.userId())
                .rating(request.rating())
                .comment(request.comment());
    }

    public ReviewDto mapToReviewDto(Review review) {
        return new ReviewDto()
                .id(review.id())
                .restaurantId(review.restaurantId())
                .userId(review.userId())
                .rating(review.rating())
                .comment(review.comment());
    }

    public Review mapToUpdateReview(Review review, ReviewRequest request) {
        return review
                .restaurantId(request.restaurantId())
                .userId(request.userId())
                .rating(request.rating())
                .comment(request.comment());
    }

}

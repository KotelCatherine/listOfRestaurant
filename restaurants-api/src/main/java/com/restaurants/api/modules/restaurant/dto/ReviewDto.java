package com.restaurants.api.modules.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Schema(description = "Данные о отзыве")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(fluent = true)
public class ReviewDto {

    @Schema(description = "Идентификатор отзыва")
    @JsonProperty("id")
    private UUID id;

    @Schema(description = "Идентификатор ресторана")
    @JsonProperty("restaurant_id")
    private UUID restaurantId;

    @Schema(description = "Идентификатор пользователя")
    @JsonProperty("user_id")
    private UUID userId;

    @Schema(description = "Рейтинг")
    @JsonProperty("rating")
    private int rating;

    @Schema(description = "Комментарий")
    @JsonProperty("comment")
    private String comment;

}

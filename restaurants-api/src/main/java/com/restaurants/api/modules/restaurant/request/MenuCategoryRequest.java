package com.restaurants.api.modules.restaurant.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Schema(description = "Запрос на создание/обновление категории меню")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true)
public class MenuCategoryRequest {

    @Schema(description = "Идентификатор ресторана")
    @JsonProperty("restaurantId")
    @NotNull
    private UUID restaurantId;

    @Schema(description = "Название категории")
    @JsonProperty("name")
    @NotBlank
    private String name;

    @Schema(description = "Описание")
    @JsonProperty("description")
    private String description;

}

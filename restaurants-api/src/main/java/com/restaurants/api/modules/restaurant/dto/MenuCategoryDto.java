package com.restaurants.api.modules.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Schema(description = "Данные о категории меню")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(fluent = true)
public class MenuCategoryDto {

    @Schema(description = "Идентификатор категории")
    @JsonProperty("id")
    private UUID id;

    @Schema(description = "Идентификатор ресторана")
    @JsonProperty("restaurantId")
    private UUID restaurantId;

    @Schema(description = "Название категории")
    @JsonProperty("name")
    private String name;

    @Schema(description = "Описание")
    @JsonProperty("description")
    private String description;

}

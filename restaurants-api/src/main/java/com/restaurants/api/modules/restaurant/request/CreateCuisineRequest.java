package com.restaurants.api.modules.restaurant.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Schema(description = "Запрос на создание новой кухни")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(fluent = true)
public class CreateCuisineRequest {

    @Schema(description = "Название ресторана")
    @JsonProperty("name")
    @NotBlank
    private String name;

    @Schema(description = "Описание ресторана")
    @JsonProperty("description")
    private String description;


}

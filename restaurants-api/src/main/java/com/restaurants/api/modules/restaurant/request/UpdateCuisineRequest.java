package com.restaurants.api.modules.restaurant.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(fluent = true)
public class UpdateCuisineRequest {

    @Schema
    @JsonProperty("name")
    private String name;

    @Schema
    @JsonProperty("description")
    private String description;

}

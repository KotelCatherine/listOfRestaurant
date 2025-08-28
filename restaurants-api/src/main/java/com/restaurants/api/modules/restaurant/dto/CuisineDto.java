package com.restaurants.api.modules.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigInteger;
import java.util.UUID;

@Schema(description = "Кухня ресторана")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(fluent = true)
public class CuisineDto {

    @Schema(description = "Идентификатор кухни")
    @JsonProperty("id")
    private UUID id;

    @Schema(description = "Идентификатор версии кухни")
    @JsonProperty("version_id")
    private BigInteger versionId;

    @Schema(description = "Название кухни")
    @JsonProperty("name")
    private String name;

    @Schema(description = "Описание кухни")
    @JsonProperty("description")
    private String description;

}

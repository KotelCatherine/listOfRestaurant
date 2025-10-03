package com.restaurants.api.modules.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Schema(description = "Данные о адресе ресторана")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(fluent = true)
public class AddressDto {

    @Schema(description = "Идентификатор адреса")
    @JsonProperty("id")
    private UUID id;

    @Schema(description = "Идентификатор ресторана")
    @JsonProperty("restaurantId")
    private UUID restaurantId;

    @Schema(description = "Страна")
    @JsonProperty("country")
    private String country;

    @Schema(description = "Город")
    @JsonProperty("city")
    private String city;

    @Schema(description = "Улица")
    @JsonProperty("street")
    private String street;

    @Schema(description = "Строение")
    @JsonProperty("building")
    private String building;

    @Schema(description = "Широта")
    @JsonProperty("latitude")
    private double latitude;

    @Schema(description = "Долгота")
    @JsonProperty("longitude")
    private double longitude;

}

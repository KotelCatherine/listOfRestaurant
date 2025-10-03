package com.restaurants.api.modules.restaurant.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Schema(description = "Запрос на добавление адреса ресторана")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(fluent = true)
public class AddressRequest {

    @Schema(description = "Страна")
    @JsonProperty("country")
    @NotBlank
    private String country;

    @Schema(description = "Город")
    @JsonProperty("city")
    @NotBlank
    private String city;

    @Schema(description = "Улица")
    @JsonProperty("street")
    @NotBlank
    private String street;

    @Schema(description = "Строение")
    @JsonProperty("building")
    @NotBlank
    private String building;

    @Schema(description = "Широта")
    @JsonProperty("latitude")
    private double latitude;

    @Schema(description = "Долгота")
    @JsonProperty("longitude")
    private double longitude;

}

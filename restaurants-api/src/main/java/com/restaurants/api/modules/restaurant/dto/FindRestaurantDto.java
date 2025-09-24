package com.restaurants.api.modules.restaurant.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Schema(description = "Поиск данных по ресторану")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(fluent = true)
public class FindRestaurantDto {

    @Schema(description = "Название ресторана")
    @JsonProperty("name")
    private String name;

    @Schema(description = "Описание ресторана")
    @JsonProperty("description")
    private String description;

    @Schema(description = "Номер телефона ресторана")
    @JsonProperty("phone")
    private String phone;

    @Schema(description = "Email почта")
    @JsonProperty("email")
    private String email;

    @Schema(description = "Вебсайт ресторана")
    @JsonProperty("website")
    private String website;


}

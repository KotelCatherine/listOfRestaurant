package com.restaurants.api.modules.restaurant.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.restaurants.api.modules.util.EmailValidation;
import com.restaurants.api.modules.util.PhoneValidation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Schema(description = "Запрос на обновление ресторана")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(fluent = true)
public class UpdateRestaurantRequest {

    @Schema(description = "Название ресторана")
    @JsonProperty("name")
    @NotBlank
    private String name;

    @Schema(description = "Описание ресторана")
    @JsonProperty("description")
    private String description;

    @Schema(description = "Номер телефона ресторана")
    @JsonProperty("phone")
    @PhoneValidation
    private String phone;

    @Schema(description = "Email почта")
    @JsonProperty("email")
    @EmailValidation
    private String email;

    @Schema(description = "Вебсайт ресторана")
    @JsonProperty("website")
    private String website;
/*

    @Schema(description = "Дата обновления данных о ресторане")
    @JsonProperty
*/

}

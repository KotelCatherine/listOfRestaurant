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


/*CreateRestaurantRequest — это класс, который представляет собой запрос на создание нового ресторана.
Он содержит поле name, которое помечено аннотацией @NotBlank, что указывает на то, что это поле обязательно для заполнения.
Этот класс может использоваться в методах контроллера для получения данных от клиента при создании нового ресторана.*/

@Schema(description = "Запрос на создание нового ресторана")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(fluent = true)
public class RestaurantRequest {

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

    @Schema(description = "Статус")
    @JsonProperty("status")
    private String status;

}

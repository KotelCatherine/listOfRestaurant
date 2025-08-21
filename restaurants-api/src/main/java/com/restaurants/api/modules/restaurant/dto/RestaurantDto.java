package com.restaurants.api.modules.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.restaurants.api.modules.util.EmailValidation;
import com.restaurants.api.modules.util.PhoneValidation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

/*1. RestaurantDto

RestaurantDto — это класс, который, скорее всего, используется для передачи данных о ресторане между слоями приложения
 (например, от контроллера к клиенту). Он может содержать поля, которые представляют собой информацию о ресторане, но в
  вашем примере они не указаны. Данный класс может использоваться для сериализации и десериализации данных при обмене с
  клиентом.
*/

@Schema(description = "Данные о ресторане")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(fluent = true)
public class RestaurantDto {

    @Schema(description = "Идентификатор ресторана")
    @JsonProperty("id")
    private UUID id;

    @Schema(description = "Название ресторана")
    @JsonProperty("name")
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

}

package com.restaurants.api.modules.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Schema(description = "Данные о пользователе")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(fluent = true)
public class UserDto {

    @Schema(description = "Идентификатор отзыва")
    @JsonProperty("id")
    private UUID id;

    @Schema(description = "Логин")
    @JsonProperty("login")
    private String login;

    @Schema(description = "Имя")
    @JsonProperty("firstName")
    private String firstName;

    @Schema(description = "Фамилия")
    @JsonProperty("lastName")
    private String lastName;

}

package com.restaurants.api.modules.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalTime;
import java.util.UUID;

@Schema(description = "Данные о расписании ресторана")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(fluent = true)
public class RestaurantScheduleDto {

    @Schema(description = "Идентификатор")
    @JsonProperty("id")
    private UUID id;

    @Schema(description = "Идентификатор ресторана")
    @JsonProperty("restaurantId")
    private UUID restaurantId;

    @Schema(description = "Числовое представление дня недели")
    @JsonProperty("dayOfWeek")
    private int dayOfWeek;

    @Schema(description = "Время открытия")
    @JsonProperty("openTime")
    private LocalTime openTime;

    @Schema(description = "Время закрытия")
    @JsonProperty("closeTime")
    private LocalTime closeTime;

    @Schema(description = "Флаг для проверки закрыт ли ресторан")
    @JsonProperty("isClosed")
    private boolean isClosed;

}

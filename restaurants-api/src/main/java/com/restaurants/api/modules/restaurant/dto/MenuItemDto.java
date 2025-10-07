package com.restaurants.api.modules.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "Данные о пункте меню")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(fluent = true)
public class MenuItemDto {

    @Schema(description = "Идентификатор пункта меню")
    @JsonProperty("id")
    private UUID id;

    @Schema(description = "Идентификатор категории меню")
    @JsonProperty("menuCategoryId")
    private UUID menuCategoryId;

    @Schema(description = "Название пункта")
    @JsonProperty("name")
    private String name;

    @Schema(description = "Описание пункта")
    @JsonProperty("description")
    private String description;

    @Schema(description = "Цена")
    @JsonProperty("price")
    private BigDecimal price;

    @Schema(description = "Ссылка на изображение")
    @JsonProperty("imageUrl")
    private String imageUrl;

}

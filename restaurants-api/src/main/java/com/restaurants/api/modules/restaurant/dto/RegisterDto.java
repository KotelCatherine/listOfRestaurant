package com.restaurants.api.modules.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
    private String login;      // Основной идентификатор
    private String firstName;  // Опционально
    private String lastName;   // Опционально
    private String password;
}
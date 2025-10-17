package com.restaurants.api.modules.restaurant.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String login;      // Основной идентификатор
    private String firstName;  // Опционально
    private String lastName;   // Опционально
    private String password;
}
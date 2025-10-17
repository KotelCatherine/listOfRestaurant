package com.restaurants.api.modules.restaurant.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;
    private String role;
    private String login;
    private String firstName;  // Опционально - для отображения на фронте
    private String lastName;
}


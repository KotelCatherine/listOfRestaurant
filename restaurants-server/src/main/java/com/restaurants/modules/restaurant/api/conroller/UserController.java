package com.restaurants.modules.restaurant.api.conroller;

import com.restaurants.api.modules.restaurant.dto.UserDto;
import com.restaurants.modules.restaurant.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping(value = "/user")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService service;

    @GetMapping("/{id}")
    public UserDto findUser(@PathVariable UUID id) {
        return service.getUser(id);
    }

    // В UserController.java
    @GetMapping("/current")
    public UserDto getCurrentUser(Authentication authentication) {

          return service.getCurrentUser(authentication);

    }

}

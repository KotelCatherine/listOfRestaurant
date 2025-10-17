package com.restaurants.modules.restaurant.api.conroller;

import com.restaurants.api.exception.RestaurantException;
import com.restaurants.api.modules.restaurant.dto.RestaurantDto;
import com.restaurants.api.modules.restaurant.request.RestaurantRequest;
import com.restaurants.modules.restaurant.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final RestaurantService restaurantService;

    @PostMapping("/restaurant")
    public RestaurantDto create(@Valid @RequestBody RestaurantRequest request) throws RestaurantException {
        return restaurantService.create(request);
    }

}

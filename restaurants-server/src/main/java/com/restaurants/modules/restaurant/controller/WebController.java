package com.restaurants.modules.restaurant.controller;

import com.restaurants.api.exception.RestaurantException;
import com.restaurants.api.modules.restaurant.dto.CuisineDto;
import com.restaurants.api.modules.restaurant.dto.RestaurantDto;
import com.restaurants.modules.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class WebController {

    private final RestaurantService restaurantService;

    @GetMapping("/")
    public String home() {
        return "redirect:/restaurants";
    }

    @GetMapping("/restaurants")
    public String restaurants(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<RestaurantDto> restaurants = restaurantService.getAllRestaurants(pageable);
        
        model.addAttribute("restaurants", restaurants.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", restaurants.getTotalPages());
        model.addAttribute("totalElements", restaurants.getTotalElements());
        model.addAttribute("size", size);
        
        return "restaurants";
    }

    @GetMapping("/restaurants/{id}")
    public String restaurantDetails(@PathVariable UUID id, Model model) throws RestaurantException {
        try {
            var restaurant = restaurantService.findById(id);
            List<CuisineDto> cuisines = restaurantService.findAllCuisines(id);
            
            model.addAttribute("restaurant", restaurant);
            model.addAttribute("cuisines", cuisines);
            
            return "restaurant-details";
        } catch (RestaurantException e) {
            model.addAttribute("error", "Ресторан не найден");
            return "error";
        }
    }

    @GetMapping("/search")
    public String searchRestaurants(
            @RequestParam String nameQuery,
            Model model) {
        
        List<RestaurantDto> restaurants = restaurantService.searchRestaurantsByName(nameQuery);
        model.addAttribute("restaurants", restaurants);
        model.addAttribute("searchQuery", nameQuery);
        
        return "search-results";
    }

    @GetMapping("/test-data")
    public String testData() {
        return "test-data";
    }

    @GetMapping("/favicon.ico")
    public String favicon() {
        return "forward:/static/favicon.ico";
    }
}

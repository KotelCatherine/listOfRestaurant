package com.restaurants.modules.restaurant.controller;

import com.restaurants.api.exception.RestaurantException;
import com.restaurants.api.modules.restaurant.dto.CuisineDto;
import com.restaurants.api.modules.restaurant.dto.RestaurantDto;
import com.restaurants.modules.restaurant.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class WebController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/")
    public String home() {
        return "redirect:/simple";
    }

    @GetMapping("/simple")
    public String simple() {
        return "simple";
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
            // Получаем полную информацию о ресторане через API
            var restaurant = restaurantService.findById(id);
            List<CuisineDto> cuisines = restaurantService.findAllCuisines(id);
            
            // Создаем RestaurantDto из FindRestaurantDto для совместимости с шаблоном
            RestaurantDto restaurantDto = new RestaurantDto();
            restaurantDto.id(id);
            restaurantDto.name(restaurant.name());
            // Остальные поля будут null, но это не критично для отображения
            
            model.addAttribute("restaurant", restaurantDto);
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

    @GetMapping("/css/style.css")
    public String css() {
        return "forward:/static/css/style.css";
    }

    @GetMapping("/js/app.js")
    public String js() {
        return "forward:/static/js/app.js";
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/debug")
    public String debug(Model model) {
        // Создаем тестовые данные для отладки
        RestaurantDto testRestaurant = new RestaurantDto();
        testRestaurant.id(UUID.randomUUID());
        testRestaurant.name("Тестовый ресторан");
        testRestaurant.description("Описание тестового ресторана");
        testRestaurant.phone("+7 (495) 123-45-67");
        testRestaurant.email("test@restaurant.ru");
        testRestaurant.website("https://test.ru");
        
        CuisineDto testCuisine = new CuisineDto();
        testCuisine.id(UUID.randomUUID());
        testCuisine.name("Тестовая кухня");
        testCuisine.description("Описание тестовой кухни");
        
        model.addAttribute("restaurant", testRestaurant);
        model.addAttribute("cuisines", List.of(testCuisine));
        
        return "debug";
    }
}

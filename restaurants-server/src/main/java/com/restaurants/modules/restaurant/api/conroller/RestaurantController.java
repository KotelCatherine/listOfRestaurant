
package com.restaurants.modules.restaurant.api.conroller;

import com.restaurants.api.exception.CuisineException;
import com.restaurants.api.exception.RestaurantException;
import com.restaurants.api.modules.restaurant.dto.AddressDto;
import com.restaurants.api.modules.restaurant.dto.CuisineDto;
import com.restaurants.api.modules.restaurant.dto.MenuCategoryDto;
import com.restaurants.api.modules.restaurant.dto.RestaurantDto;
import com.restaurants.api.modules.restaurant.request.AddressRequest;
import com.restaurants.api.modules.restaurant.request.RestaurantRequest;
import com.restaurants.api.modules.restaurant.request.MenuCategoryRequest;
import com.restaurants.api.resource.RestaurantResource;
import com.restaurants.modules.restaurant.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping(value = "restaurant")
@RestController
@RequiredArgsConstructor
public class RestaurantController implements RestaurantResource {

    private final RestaurantService restaurantService;

    @Override
    @PostMapping
    public RestaurantDto create(@Valid @RequestBody RestaurantRequest request) throws RestaurantException {
        return restaurantService.create(request);
    }

    @Override
    @GetMapping("/{id}")
    public RestaurantDto findRestaurant(@PathVariable UUID id) throws RestaurantException {
        return restaurantService.findById(id);
    }

    @Override
    @GetMapping
    public Page<RestaurantDto> getAllRestaurants(@ParameterObject Pageable pageable) {
        return restaurantService.getAllRestaurants(pageable);
    }

    @Override
    @GetMapping("/search")
    public List<RestaurantDto> searchRestaurantsByName(@RequestParam String nameQuery) {
        return restaurantService.searchRestaurantsByName(nameQuery);
    }

    @Override
    @PutMapping("/{id}/update")
    public RestaurantDto update(
            @PathVariable UUID id,
            @RequestBody RestaurantRequest request) throws RestaurantException {
        return restaurantService.update(id, request);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) throws RestaurantException {
        restaurantService.delete(id);
    }

    @Override
    @GetMapping("/{id}/cuisines")
    public List<CuisineDto> findAllCuisinesByRestaurantId(@PathVariable UUID id) throws RestaurantException {
        return restaurantService.findAllCuisines(id);
    }

    @Override
    @GetMapping("/{cuisineId}/findAllRestaurant")
    public List<RestaurantDto> findAllRestaurantByCuisineId(@PathVariable UUID cuisineId) throws RestaurantException {
        return restaurantService.findAllRestaurants(cuisineId);
    }

    @Override
    @PostMapping("/cuisines/{restaurantId}/{cuisineId}")
    public CuisineDto createRestaurantCuisine(@PathVariable UUID restaurantId, @PathVariable UUID cuisineId) throws RestaurantException, CuisineException {
        return restaurantService.createRestaurantCuisine(restaurantId, cuisineId);
    }

    @Override
    @PostMapping("/{restaurantId}/address")
    public AddressDto createAddress(@PathVariable UUID restaurantId, @Valid @RequestBody AddressRequest request) throws RestaurantException {
        return restaurantService.createAddress(restaurantId, request);
    }

    @Override
    @PostMapping("/{restaurantId}/menuCategory")
    public MenuCategoryDto createMenuCategory(@PathVariable UUID restaurantId, @Valid @RequestBody MenuCategoryRequest request) throws RestaurantException {
        return restaurantService.createMenuCategory(restaurantId, request);
    }

}

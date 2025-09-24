
package com.restaurants.modules.restaurant.api.conroller;

import com.restaurants.api.exception.RestaurantException;
import com.restaurants.api.modules.restaurant.dto.CuisineDto;
import com.restaurants.api.modules.restaurant.dto.FindRestaurantDto;
import com.restaurants.api.modules.restaurant.dto.RestaurantDto;
import com.restaurants.api.modules.restaurant.request.CreateRestaurantRequest;
import com.restaurants.api.modules.restaurant.request.UpdateRestaurantRequest;
import com.restaurants.api.resource.RestaurantResource;
import com.restaurants.modules.restaurant.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;

/*RestaurantController — это контроллер, который обрабатывает HTTP-запросы, связанные с ресторанами.
Он использует RestaurantService для выполнения бизнес-логики. Метод create(CreateRestaurantRequest request)
должен обрабатывать запрос на создание нового ресторана. В текущем виде он возвращает null, но в реальном приложении
он должен будет создавать новый объект Restaurant на основе данных из CreateRestaurantRequest,
сохранять его через RestaurantService и возвращать объект RestaurantDto.*/

@RequestMapping(value = "restaurant")
@RestController
@RequiredArgsConstructor
public class RestaurantController implements RestaurantResource {

    @Autowired
    private final RestaurantService restaurantService;

    @Override
    @PostMapping
    public RestaurantDto create(@Valid @RequestBody CreateRestaurantRequest request) throws RestaurantException {
        return restaurantService.create(request);
    }

    @Override
    @GetMapping("/{id}")
    public FindRestaurantDto findRestaurant(@PathVariable UUID id) throws RestaurantException {
        return restaurantService.findById(id);
    }

    @Override
    @GetMapping
    public Page<RestaurantDto> getAllRestaurants(@ParameterObject Pageable pageable) {
        return restaurantService.getAllRestaurants(pageable);
    }

    @Override
    @GetMapping("/search")
    public List<RestaurantDto> searchRestaurantsByName(
            @RequestParam String nameQuery) {
        return restaurantService.searchRestaurantsByName(nameQuery);
    }

    @Override
    @PostMapping("/{id}/update")
    public RestaurantDto update(
            @PathVariable UUID id,
            @RequestBody UpdateRestaurantRequest request) throws RestaurantException {
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
    @GetMapping("/{id}/findAllRestaurant")
    public List<RestaurantDto> findAllRestaurantByCuisineId(UUID cuisineId) throws RestaurantException {
        return restaurantService.findAllRestaurants(cuisineId);
    }


}

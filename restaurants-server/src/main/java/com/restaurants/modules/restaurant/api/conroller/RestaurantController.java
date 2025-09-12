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
import org.springframework.http.ResponseEntity;
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

@RequestMapping(value = "restaurant", method = {RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.GET})
@RestController
@RequiredArgsConstructor
@Validated
public class RestaurantController implements RestaurantResource {

    @Autowired
    private final RestaurantService restaurantService;

    @Override
    @PostMapping
    public ResponseEntity<RestaurantDto> create(@RequestBody @Valid CreateRestaurantRequest request) throws RestaurantException {

        RestaurantDto restaurantDto = restaurantService.create(request);

        return ResponseEntity.ok(restaurantDto);

    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<FindRestaurantDto> findRestaurant(@PathVariable UUID id) throws RestaurantException {

        FindRestaurantDto restaurantDto = restaurantService.findById(id);

        return ResponseEntity.ok(restaurantDto);

    }

    @Override
    @GetMapping
    public ResponseEntity<Page<RestaurantDto>> getAllRestaurants(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(restaurantService.getAllRestaurants(pageable));
    }

    @Override
    @GetMapping("/search")
    public ResponseEntity<List<RestaurantDto>> searchRestaurantsByName(
            @RequestParam String nameQuery) {
        List<RestaurantDto> restaurants = restaurantService.searchRestaurantsByName(nameQuery);
        return ResponseEntity.ok(restaurants);
    }

    @Override
    @PostMapping("/{id}")
    public ResponseEntity<RestaurantDto> update(
            @PathVariable UUID id,
            @RequestBody UpdateRestaurantRequest request) throws RestaurantException {
        RestaurantDto updatedRestaurant = restaurantService.update(id, request);
        return ResponseEntity.ok(updatedRestaurant);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws RestaurantException {

        restaurantService.delete(id);

        return ResponseEntity.ok().build();

    }

    @Override
    @GetMapping("/{id}/cuisines")
    public List<CuisineDto> findAllCuisinesByRestaurantId(@PathVariable UUID id) throws RestaurantException {
        return restaurantService.findAllCuisines(id);
    }

    @Override
    public List<RestaurantDto> findAllRestaurantByCuisineId(UUID cuisineId) throws RestaurantException {
        return restaurantService.findAllRestaurants(cuisineId);
    }


}


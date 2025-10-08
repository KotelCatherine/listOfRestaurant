package com.restaurants.modules.restaurant.api.conroller;

import com.restaurants.api.exception.RestaurantScheduleException;
import com.restaurants.api.modules.restaurant.dto.RestaurantScheduleDto;
import com.restaurants.api.modules.restaurant.request.RestaurantScheduleRequest;
import com.restaurants.api.resource.RestaurantScheduleResource;
import com.restaurants.modules.restaurant.service.RestaurantScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping(value = "/restaurantSchedule")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class RestaurantScheduleController implements RestaurantScheduleResource {

    private final RestaurantScheduleService service;

    @Override
    @PostMapping
    public RestaurantScheduleDto create(@RequestBody RestaurantScheduleRequest request) {
        return service.create(request);
    }

    @Override
    @GetMapping("/{id}")
    public RestaurantScheduleDto findById(@PathVariable UUID id) throws RestaurantScheduleException {
        return service.findById(id);
    }

    @Override
    @GetMapping("/{restaurantId}/restaurantSchedule")
    public List<RestaurantScheduleDto> findCompleteHoursRestaurant(@PathVariable UUID restaurantId) throws RestaurantScheduleException {
        return service.findCompleteHoursRestaurant(restaurantId);
    }

    @Override
    @PutMapping("/{id}")
    public RestaurantScheduleDto update(@PathVariable UUID id, @RequestBody RestaurantScheduleRequest request) throws RestaurantScheduleException {
        return service.update(id, request);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) throws RestaurantScheduleException {
        service.delete(id);
    }

}

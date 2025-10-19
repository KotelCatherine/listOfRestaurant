package com.restaurants.modules.restaurant.api.conroller;

import com.restaurants.api.exception.AddressException;
import com.restaurants.api.exception.RestaurantException;
import com.restaurants.api.modules.restaurant.dto.AddressDto;
import com.restaurants.api.modules.restaurant.request.AddressRequest;
import com.restaurants.api.resource.AddressResource;
import com.restaurants.modules.restaurant.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping(value = "/address")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AddressController implements AddressResource {

    private final AddressService service;

    @Override
    @PostMapping("/{restaurantId}/address")
    public AddressDto createAddress(@PathVariable UUID restaurantId, @Valid @RequestBody AddressRequest request) throws RestaurantException {
        return service.createAddress(restaurantId, request);
    }

    @Override
    @GetMapping("/{id}")
    public AddressDto findAddressById(@PathVariable UUID id) throws AddressException {
        return service.findById(id);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public AddressDto findAddressByRestaurantId(@PathVariable UUID restaurantId) throws RestaurantException {
        return service.findAddressByRestaurantId(restaurantId);
    }

    @GetMapping("/cities")
    public List<String> findAllCities() {
        return service.findAllCities();
    }

    @Override
    @PutMapping("/{id}")
    public AddressDto update(@PathVariable UUID id, @RequestBody AddressRequest request) throws AddressException {
        return service.update(id, request);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) throws AddressException {
        service.delete(id);
    }

}

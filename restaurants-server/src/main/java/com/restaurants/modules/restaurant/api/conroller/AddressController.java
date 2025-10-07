package com.restaurants.modules.restaurant.api.conroller;

import com.restaurants.api.exception.AddressException;
import com.restaurants.api.modules.restaurant.dto.AddressDto;
import com.restaurants.api.modules.restaurant.request.AddressRequest;
import com.restaurants.api.resource.AddressResource;
import com.restaurants.modules.restaurant.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping(value = "address")
@RestController
@RequiredArgsConstructor
public class AddressController implements AddressResource {

    private final AddressService service;

    @Override
    @GetMapping("/{id}")
    public AddressDto findAddressById(@PathVariable UUID id) throws AddressException {
        return service.findById(id);
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

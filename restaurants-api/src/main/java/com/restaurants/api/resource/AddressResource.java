package com.restaurants.api.resource;

import com.restaurants.api.exception.AddressException;
import com.restaurants.api.modules.restaurant.dto.AddressDto;
import com.restaurants.api.modules.restaurant.request.AddressRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Address", description = "Адрес")
public interface AddressResource {

/*
    @PostMapping("/{restaurantId}")
    @Operation(summary = "createAddressByRestaurantIdUsingPost", description = "Создание адреса")
    @ResponseStatus(HttpStatus.CREATED)
    AddressDto create(@PathVariable UUID restaurantId, @RequestBody AddressRequest request) throws RestaurantException;
*/

    @GetMapping("/{id}")
    @Operation(summary = "findAddressByIdUsingGet", description = "Поиск адреса по идентификатору")
    @ResponseStatus(HttpStatus.OK)
    AddressDto findAddressById(@PathVariable UUID id) throws AddressException;

    @PutMapping("/{id}")
    @Operation(summary = "updateByIdUsingPost", description = "Обновление адреса")
    @ResponseStatus(HttpStatus.OK)
    AddressDto update(@PathVariable UUID id, @RequestBody AddressRequest request) throws AddressException;

    @DeleteMapping("/{id}")
    @Operation(summary = "deleteAddressByIdUsingDelete", description = "Удаление адреса")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable UUID id) throws AddressException;

}

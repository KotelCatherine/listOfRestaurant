package com.restaurants.modules.restaurant.mapper;

import com.restaurants.api.modules.restaurant.dto.AddressDto;
import com.restaurants.api.modules.restaurant.request.AddressRequest;
import com.restaurants.modules.restaurant.entity.Address;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AddressMapper {

    public Address mapToEntity(UUID restaurantId, @Valid AddressRequest request) {
        return new Address()
                .id(UUID.randomUUID())
                .restaurantId(restaurantId)
                .country(request.country())
                .city(request.city())
                .street(request.street())
                .latitude(request.latitude())
                .longitude(request.longitude());
    }

    public AddressDto mapToAddressDto(Address address) {
        return new AddressDto()
                .id(address.id())
                .restaurantId(address.restaurantId())
                .country(address.country())
                .city(address.city())
                .street(address.street())
                .latitude(address.latitude())
                .longitude(address.longitude());
    }

    public Address mapToAddress(Address address, AddressRequest request) {
        return address
                .country(request.country())
                .city(request.city())
                .street(request.street())
                .building(request.building())
                .latitude(request.latitude())
                .longitude(request.longitude());
    }

}

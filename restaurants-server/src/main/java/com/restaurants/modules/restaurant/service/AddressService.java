package com.restaurants.modules.restaurant.service;

import com.restaurants.api.exception.AddressErrorCodeEnum;
import com.restaurants.api.exception.AddressException;
import com.restaurants.api.exception.RestaurantErrorCodeEnum;
import com.restaurants.api.exception.RestaurantException;
import com.restaurants.api.modules.restaurant.dto.AddressDto;
import com.restaurants.api.modules.restaurant.request.AddressRequest;
import com.restaurants.modules.restaurant.entity.Address;
import com.restaurants.modules.restaurant.mapper.AddressMapper;
import com.restaurants.modules.restaurant.repository.AddressRepository;
import com.restaurants.modules.restaurant.repository.RestaurantRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Slf4j
@Validated
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class AddressService {

    private final RestaurantRepository restaurantRepository;
    private final AddressRepository repository;

    private final AddressMapper mapper;

    @Transactional(rollbackFor = Exception.class)
    public AddressDto createAddress(UUID restaurantId, @Valid AddressRequest request) throws RestaurantException {

        if (!restaurantRepository.existsById(restaurantId)) {
            throw new RestaurantException(RestaurantErrorCodeEnum.NOT_FOUND_RESTAURANT_BY_ID);
        }

        Address address = mapper.mapToEntity(restaurantId, request);

        repository.saveAndFlush(address);

        return mapper.mapToAddressDto(address);

    }

    public AddressDto findById(UUID id) throws AddressException {

        Address address = repository.findById(id)
                .orElseThrow(() -> new AddressException(AddressErrorCodeEnum.NOT_FOUND_ADDRESS));

        return mapper.mapToAddressDto(address);

    }

    @Transactional(rollbackFor = Exception.class)
    public AddressDto update(UUID id, AddressRequest request) throws AddressException {

        Address address = repository.findById(id)
                .orElseThrow(() -> new AddressException(AddressErrorCodeEnum.NOT_FOUND_ADDRESS));

        address = mapper.mapToAddress(address, request);

        repository.saveAndFlush(address);

        return mapper.mapToAddressDto(address);

    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(UUID id) throws AddressException {

        if (!repository.existsById(id)) {
            throw new AddressException(AddressErrorCodeEnum.NOT_FOUND_ADDRESS);
        }

        repository.deleteById(id);

    }

    public List<String> findAllCities() {
        return repository.findAllCities();
    }

}

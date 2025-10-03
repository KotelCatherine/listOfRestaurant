package com.restaurants.modules.restaurant.service;

import com.restaurants.api.exception.AddressErrorCodeEnum;
import com.restaurants.api.exception.AddressException;
import com.restaurants.api.modules.restaurant.dto.AddressDto;
import com.restaurants.api.modules.restaurant.request.AddressRequest;
import com.restaurants.modules.restaurant.entity.Address;
import com.restaurants.modules.restaurant.mapper.AddressMapper;
import com.restaurants.modules.restaurant.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Slf4j
@Validated
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository repository;
    private final AddressMapper mapper;

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

}

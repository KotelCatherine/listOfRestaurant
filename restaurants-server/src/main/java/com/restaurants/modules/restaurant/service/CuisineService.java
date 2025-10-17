package com.restaurants.modules.restaurant.service;

import com.restaurants.api.exception.CuisineErrorCodeEnum;
import com.restaurants.api.exception.CuisineException;
import com.restaurants.api.modules.restaurant.dto.CuisineDto;
import com.restaurants.api.modules.restaurant.request.CuisineRequest;
import com.restaurants.modules.restaurant.entity.Cuisine;
import com.restaurants.modules.restaurant.mapper.CuisineMapper;
import com.restaurants.modules.restaurant.repository.CuisinesRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
public class CuisineService {

    private final CuisinesRepository repository;
    private final CuisineMapper mapper;

    @Transactional(rollbackFor = Exception.class)
    public CuisineDto createCuisine(@Valid CuisineRequest request) throws CuisineException {

        chekName(request.name());
        Cuisine cuisine = mapper.mapToEntity(request);

        repository.saveAndFlush(cuisine);

        return mapper.mapToCuisineDto(cuisine);

    }

    public List<CuisineDto> getAllCuisines() {

        List<Cuisine> cuisines = repository.findAll();

        return cuisines.stream()
                .map(mapper::mapToCuisineDto)
                .toList();

    }

    @Transactional(rollbackFor = Exception.class)
    public CuisineDto updateCuisine(UUID cuisineId, @Valid CuisineRequest request) throws CuisineException {

        Cuisine cuisine = repository.findById(cuisineId)
                .orElseThrow(() -> new CuisineException(CuisineErrorCodeEnum.NOT_FOUND_CUISINE_BY_ID));

        cuisine = mapper.mapToCuisine(request, cuisine);

        repository.saveAndFlush(cuisine);

        return mapper.mapToCuisineDto(cuisine);

    }

    public CuisineDto findById(UUID id) throws CuisineException {

        Cuisine cuisine = repository.findById(id)
                .orElseThrow(() -> new CuisineException(CuisineErrorCodeEnum.NOT_FOUND_CUISINE_BY_ID));

        return mapper.mapToCuisineDto(cuisine);

    }

    private void chekName(@NotBlank String name) throws CuisineException {

        if (repository.existsByNameIgnoreCase(name)) {
            throw new CuisineException(CuisineErrorCodeEnum.CUISINE_NAME_ALREADY_EXISTS);
        }

    }

    @Transactional(rollbackFor =  Exception.class)
    public void delete(UUID id) {
        repository.deleteById(id);
    }

}

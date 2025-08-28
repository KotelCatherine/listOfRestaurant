package com.restaurants.modules.restaurant.mapper;

import com.restaurants.api.modules.restaurant.dto.CuisineDto;
import com.restaurants.api.modules.restaurant.request.CreateCuisineRequest;
import com.restaurants.modules.restaurant.entity.Cuisine;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CuisineMapper {

    public Cuisine mapToEntity(@Valid CreateCuisineRequest request) {
        return new Cuisine()
                .id(UUID.randomUUID())
                .versionId(BigInteger.ONE)
                .name(request.name())
                .description(request.description());
    }

    public CuisineDto mapToCuisineDto(Cuisine cuisine) {
        return new CuisineDto()
                .id(cuisine.id())
                .versionId(cuisine.versionId())
                .name(cuisine.name())
                .description(cuisine.description());
    }

}

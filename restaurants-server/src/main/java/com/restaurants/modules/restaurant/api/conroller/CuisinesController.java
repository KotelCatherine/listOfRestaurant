package com.restaurants.modules.restaurant.api.conroller;

import com.restaurants.api.exception.CuisineException;
import com.restaurants.api.modules.restaurant.dto.CuisineDto;
import com.restaurants.api.modules.restaurant.request.CuisineRequest;
import com.restaurants.api.resource.CuisineResource;
import com.restaurants.modules.restaurant.service.CuisineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping(value = "cuisine")
@RestController
@RequiredArgsConstructor
public class CuisinesController implements CuisineResource {

    private final CuisineService cuisineService;

    @Override
    @GetMapping
    public Page<CuisineDto> getAllCuisines(@ParameterObject Pageable pageable) {
        return cuisineService.getAllCuisines(pageable);
    }

    @Override
    @PostMapping
    public CuisineDto createCuisine(@Valid @RequestBody CuisineRequest request) throws CuisineException {
        return cuisineService.createCuisine(request);
    }

    @Override
    @PutMapping("/{id}/update")
    public CuisineDto updateCuisine(@PathVariable UUID id, @Valid @RequestBody CuisineRequest request) throws CuisineException {
        return cuisineService.updateCuisine(id, request);
    }

    @Override
    @DeleteMapping("/{id}")
    public void deleteCuisine(@PathVariable UUID id) {

    }

}

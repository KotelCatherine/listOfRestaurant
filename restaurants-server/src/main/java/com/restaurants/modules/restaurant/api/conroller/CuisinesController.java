package com.restaurants.modules.restaurant.api.conroller;

import com.restaurants.api.exception.CuisineException;
import com.restaurants.api.modules.restaurant.dto.CuisineDto;
import com.restaurants.api.modules.restaurant.request.CreateCuisineRequest;
import com.restaurants.api.modules.restaurant.request.UpdateCuisineRequest;
import com.restaurants.api.resource.CuisineResource;
import com.restaurants.modules.restaurant.service.CuisineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

@RequestMapping(value = "cuisine", method = {RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.GET})
@RestController
@RequiredArgsConstructor
@Validated
public class CuisinesController implements CuisineResource {

    @Autowired
    private CuisineService cuisineService;

    @Override
    @GetMapping("/cuisines")
    public Page<CuisineDto> getAllCuisines(@ParameterObject Pageable pageable) {
        return cuisineService.getAllCuisines(pageable);
    }

    @Override
    @PostMapping
    public CuisineDto createCuisine(@Valid @RequestBody CreateCuisineRequest request) throws CuisineException {
        return cuisineService.createCuisine(request);
    }

    @Override
    public CuisineDto updateCuisine(@Valid @RequestBody UpdateCuisineRequest request) {
        return cuisineService.updateCuisine(request);
    }

    @Override
    @DeleteMapping("/{id}")
    public void deleteCuisine(@PathVariable UUID id) {

    }

}
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
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("cuisine")
@RestController
@RequiredArgsConstructor
@Validated
public class CuisinesController implements CuisineResource {

    @Autowired
    private CuisineService cuisineService;

    @Override
    @GetMapping("/cuisines")
    public ResponseEntity<Page<CuisineDto>> getAllCuisines(@ParameterObject Pageable pageable) {

        Page<CuisineDto> allCuisines = cuisineService.getAllCuisines(pageable);

        return ResponseEntity.ok(allCuisines);

    }

    @Override
    @PostMapping
    public ResponseEntity<CuisineDto> createCuisine(@Valid @RequestBody CreateCuisineRequest request) throws CuisineException {

        CuisineDto cuisine = cuisineService.createCuisine(request);

        return ResponseEntity.ok(cuisine);

    }

    @Override
    public ResponseEntity<CuisineDto> updateCuisine(@Valid @RequestBody UpdateCuisineRequest request) {

        CuisineDto cuisineDto = cuisineService.updateCuisine(request);

        return ResponseEntity.ok(cuisineDto);

    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCuisine(@PathVariable UUID id) {
        return ResponseEntity.ok().build();
    }

}

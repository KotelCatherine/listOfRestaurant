package com.restaurants.modules.restaurant.api.conroller;

import com.restaurants.api.exception.CuisineException;
import com.restaurants.api.modules.restaurant.dto.CuisineDto;
import com.restaurants.api.modules.restaurant.request.CuisineRequest;
import com.restaurants.api.resource.CuisineResource;
import com.restaurants.modules.restaurant.service.CuisineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping(value = "/cuisines")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CuisinesController implements CuisineResource {

    private final CuisineService service;

    @Override
    @GetMapping
    public List<CuisineDto> getAllCuisines() {
        return service.getAllCuisines();
    }

    @Override
    @PostMapping
    public CuisineDto createCuisine(@Valid @RequestBody CuisineRequest request) throws CuisineException {
        return service.createCuisine(request);
    }

    @Override
    @GetMapping("/{id}")
    public CuisineDto findById(@PathVariable UUID id) throws CuisineException {
        return service.findById(id);
    }

    @Override
    @PutMapping("/{id}/update")
    public CuisineDto updateCuisine(@PathVariable UUID id, @Valid @RequestBody CuisineRequest request) throws CuisineException {
        return service.updateCuisine(id, request);
    }

    @Override
    @DeleteMapping("/{id}")
    public void deleteCuisine(@PathVariable UUID id) {
        service.delete(id);
    }

}

package com.restaurants.modules.restaurant.service;

import com.restaurants.api.exception.RestaurantScheduleErrorCodeEnum;
import com.restaurants.api.exception.RestaurantScheduleException;
import com.restaurants.api.modules.restaurant.dto.RestaurantScheduleDto;
import com.restaurants.api.modules.restaurant.request.RestaurantScheduleRequest;
import com.restaurants.modules.restaurant.entity.RestaurantSchedule;
import com.restaurants.modules.restaurant.mapper.RestaurantScheduleMapper;
import com.restaurants.modules.restaurant.repository.RestaurantScheduleRepository;
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
public class RestaurantScheduleService {

    private final RestaurantScheduleMapper mapper;
    private final RestaurantScheduleRepository repository;

    @Transactional(rollbackFor = Exception.class)
    public RestaurantScheduleDto create(@Valid RestaurantScheduleRequest request) {

        RestaurantSchedule restaurantSchedule = mapper.mapToRestaurantSchedule(request);

        restaurantSchedule = repository.saveAndFlush(restaurantSchedule);

        return mapper.mapToRestaurantScheduleDto(restaurantSchedule);

    }

    public RestaurantScheduleDto findById(UUID id) throws RestaurantScheduleException {

        RestaurantSchedule restaurantSchedule = repository.findById(id)
                .orElseThrow(() -> new RestaurantScheduleException(RestaurantScheduleErrorCodeEnum.NOT_FOUND_RESTAURANT_BY_ID));

        return mapper.mapToRestaurantScheduleDto(restaurantSchedule);

    }

    public List<RestaurantScheduleDto> findCompleteHoursRestaurant(UUID restaurantId) throws RestaurantScheduleException {

        if (!repository.existsByRestaurantId(restaurantId)) {
            throw new RestaurantScheduleException(RestaurantScheduleErrorCodeEnum.NOT_FOUND_RESTAURANT_SCHEDULE_BY_ID);
        }

        List<RestaurantSchedule> schedules = repository.findAllByRestaurantId(restaurantId);

        return schedules.stream()
                .map(mapper::mapToRestaurantScheduleDto)
                .toList();

    }

    @Transactional(rollbackFor = Exception.class)
    public RestaurantScheduleDto update(UUID id, @Valid RestaurantScheduleRequest request) throws RestaurantScheduleException {

        RestaurantSchedule restaurantSchedule = repository.findById(id)
                .orElseThrow(() -> new RestaurantScheduleException(RestaurantScheduleErrorCodeEnum.NOT_FOUND_RESTAURANT_BY_ID));

        restaurantSchedule = mapper.mapToUpdateRestaurantSchedule(restaurantSchedule, request);

        repository.saveAndFlush(restaurantSchedule);

        return mapper.mapToRestaurantScheduleDto(restaurantSchedule);

    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(UUID id) throws RestaurantScheduleException {

        if (!repository.existsById(id)) {
            throw new RestaurantScheduleException(RestaurantScheduleErrorCodeEnum.NOT_FOUND_RESTAURANT_BY_ID);
        }

        repository.deleteById(id);

    }

}

package com.restaurants.modules.restaurant.mapper;

import com.restaurants.api.modules.restaurant.dto.RestaurantScheduleDto;
import com.restaurants.api.modules.restaurant.request.RestaurantScheduleRequest;
import com.restaurants.modules.restaurant.entity.RestaurantSchedule;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RestaurantScheduleMapper {

    public RestaurantSchedule mapToRestaurantSchedule(RestaurantScheduleRequest request) {
        return new RestaurantSchedule()
                .id(UUID.randomUUID())
                .restaurantId(request.restaurantId())
                .dayOfWeek(request.dayOfWeek())
                .openTime(request.openTime())
                .closeTime(request.closeTime())
                .isClosed(request.isClosed());
    }

    public RestaurantScheduleDto mapToRestaurantScheduleDto(RestaurantSchedule restaurantSchedule) {
        return new RestaurantScheduleDto()
                .id(restaurantSchedule.id())
                .restaurantId(restaurantSchedule.restaurantId())
                .dayOfWeek(restaurantSchedule.dayOfWeek())
                .openTime(restaurantSchedule.openTime())
                .closeTime(restaurantSchedule.closeTime())
                .isClosed(restaurantSchedule.isClosed());
    }


    public RestaurantSchedule mapToUpdateRestaurantSchedule(RestaurantSchedule restaurantSchedule, @Valid RestaurantScheduleRequest request) {
        return restaurantSchedule
                .restaurantId(request.restaurantId())
                .dayOfWeek(request.dayOfWeek())
                .openTime(request.openTime())
                .closeTime(request.closeTime())
                .isClosed(restaurantSchedule.isClosed());
    }

}

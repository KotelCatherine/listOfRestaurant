package com.restaurants.modules.restaurant.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalTime;
import java.util.UUID;

@Data
@Entity(name = "restaurant_schedules")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Accessors(fluent = true)
public class RestaurantSchedule {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "restaurant_id")
    private UUID restaurantId;

    @Column(name = "day_of_week")
    private int dayOfWeek;

    @Column(name = "open_time")
    private LocalTime openTime;

    @Column(name = "close_time")
    private LocalTime closeTime;

    @Column(name = "is_closed")
    private boolean isClosed;

}

package com.restaurants.modules.restaurant.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Entity(name = "menu_categories")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Accessors(fluent = true)
public class MenuCategory {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "restaurant_id")
    private UUID restaurantId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

}

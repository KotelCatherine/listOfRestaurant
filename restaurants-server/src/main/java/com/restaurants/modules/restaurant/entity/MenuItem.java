package com.restaurants.modules.restaurant.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.UUID;

@Entity(name = "menu_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Accessors(fluent = true)
public class MenuItem {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "menu_category_id")
    private UUID menuCategoryId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "image_url")
    private String imageUrl;

}

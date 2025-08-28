package com.restaurants.modules.restaurant.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigInteger;
import java.util.UUID;

@Data
@Entity(name = "cuisines")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Accessors(fluent = true)
public class Cuisine {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "version_id")
    private BigInteger versionId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

}

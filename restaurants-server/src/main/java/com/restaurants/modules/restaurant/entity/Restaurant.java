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
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;


/*Restaurant — это сущность, которая представляет собой ресторан в базе данных. Она аннотирована с помощью @Entity,
что указывает на то, что этот класс будет отображен на таблицу в базе данных. Поля класса соответствуют колонкам таблицы.
Например, поле name соответствует колонке name в базе данных.*/

@Data
@Entity(name = "restaurants")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Accessors(fluent = true)
public class Restaurant {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "version_id")
    private BigInteger versionId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "website")
    private String website;

    @Column(name = "status")
    private String status;

    @Column(name = "opening_date")
    private Date openingDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private String createdBy;

}

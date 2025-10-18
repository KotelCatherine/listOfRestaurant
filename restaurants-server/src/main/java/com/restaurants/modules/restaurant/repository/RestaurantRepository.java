package com.restaurants.modules.restaurant.repository;

import com.restaurants.modules.restaurant.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/*RestaurantRepository — это интерфейс репозитория, который будет использоваться для взаимодействия с базой данных.
 Он может расширять один из интерфейсов Spring Data JPA (например, JpaRepository), чтобы получить стандартные методы для
 работы с сущностью Restaurant, такие как сохранение, поиск и удаление ресторанов.*/

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {

    @Query("""         
            select r from restaurants r
            where lower(r.name) like lower(concat('%', :query, '%'))
            """)
    List<Restaurant> findByName(@Param("query") String query);

    @Query("SELECT r FROM restaurants r WHERE r.id IN :ids")
    Page<Restaurant> findByIds(@Param("ids") List<UUID> ids, Pageable pageable);


    boolean existsByNameIgnoreCase(String name);

    boolean existsByName(String name);

}

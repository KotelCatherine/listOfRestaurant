package com.restaurants.modules.restaurant.service;

import com.restaurants.api.modules.restaurant.dto.UserDto;
import com.restaurants.modules.restaurant.entity.User;
import com.restaurants.modules.restaurant.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public UserDto getUser(UUID id) {

        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        return new UserDto()
                .id(user.getId())
                .login(user.getLogin())
                .firstName(user.getFirstName())
                .lastName(user.getLastName());

    }

}

// src/main/java/com/example/bankcards/service/UserService.java
package com.example.bankcards.service;

import com.example.bankcards.dto.UserDto;
import com.example.bankcards.dto.request.UserCreateRequest;
import com.example.bankcards.dto.request.UserUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    /**
     * Create a new user: in Keycloak and in local database.
     */
    UserDto createUser(UserCreateRequest request);

    /**
     * List users, optionally filtering by email or phone substring.
     */
    Page<UserDto> getUsers(String search, Pageable pageable);

    /**
     * Get a single user by local database ID.
     */
    UserDto getUserById(Long id);

    /**
     * Update a user: password and/or role in Keycloak, plus local profile.
     */
    UserDto updateUser(UserUpdateRequest request);

    /**
     * Delete a user by local ID (and remove from Keycloak).
     */
    void deleteUser(Long id);
}

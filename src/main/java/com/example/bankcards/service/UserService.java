package com.example.bankcards.service;

import com.example.bankcards.dto.UserDto;
import com.example.bankcards.dto.request.UserCreateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * User service interface.
 */
public interface UserService {
    UserDto createUser(UserCreateRequest request);
    UserDto getUserById(Long id);
    Page<UserDto> getUsers(String search, Pageable pageable);
    void deleteUser(Long id);
}

package com.example.bankcards.service.impl;

import com.example.bankcards.dto.UserDto;
import com.example.bankcards.dto.request.UserCreateRequest;
import com.example.bankcards.dto.request.UserUpdateRequest;
import com.example.bankcards.entity.User;
import com.example.bankcards.enums.UserStatus;
import com.example.bankcards.mapper.UserMapper;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.service.KeycloakService;
import com.example.bankcards.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final KeycloakService keycloakService;
    private final UserMapper userMapper;

    @Override
    public UserDto createUser(UserCreateRequest request) {
        log.info("Creating user '{}'", request.username());
        String kcId = keycloakService.createKeycloakUser(request);
        User user = userMapper.toEntity(request);
        user.setKeycloakId(kcId);
        user.setStatus(UserStatus.ACTIVE);
        User saved = userRepository.save(user);
        log.debug("User created: id={}, keycloakId={}", saved.getId(), kcId);
        return userMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDto> getUsers(String search, Pageable pageable) {
        Page<User> page = (search == null || search.isBlank())
                ? userRepository.findAll(pageable)
                : userRepository.findByEmailContainingIgnoreCaseOrPhoneContainingIgnoreCase(search, search, pageable);
        return page.map(userMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("User not found with id: %d", id)));
        return userMapper.toDto(user);
    }

    @Override
    public UserDto updateUser(UserUpdateRequest request) {
        log.info("Updating user id={}", request.id());
        User user = userRepository.findById(request.id())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("User not found with id: %d", request.id())));
        keycloakService.updateKeycloakUser(request);
        userMapper.updateEntityFromRequest(request, user);
        User updated = userRepository.save(user);
        log.debug("User updated: id={}, keycloakId={}", updated.getId(), updated.getKeycloakId());
        return userMapper.toDto(updated);
    }

    @Override
    public void deleteUser(Long id) {
        log.info("Deleting user id={}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("User not found with id: %d", id)));
        keycloakService.deleteKeycloakUser(user.getKeycloakId());
        userRepository.deleteById(id);
        log.debug("User deleted: id={}, keycloakId={}", id, user.getKeycloakId());
    }
}

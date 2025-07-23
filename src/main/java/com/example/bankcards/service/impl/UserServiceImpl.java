package com.example.bankcards.service.impl;

import com.example.bankcards.dto.UserDto;
import com.example.bankcards.dto.request.UserCreateRequest;
import com.example.bankcards.entity.User;
import com.example.bankcards.enums.UserStatus;
import com.example.bankcards.enums.Role;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserCreateRequest request) {
        log.info("Creating user: {}", request.username());
        if (userRepository.existsByUsername(request.username()) || userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("User already exists");
        }
        User user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .fullName(request.fullName())
                .email(request.email())
                .role(Role.valueOf(request.role()))
                .status(UserStatus.ACTIVE)
                .build();
        user = userRepository.save(user);
        return toDto(user);
    }

    @Override
    public UserDto getUserById(Long id) {
        log.info("Fetching user by id: {}", id);
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return toDto(user);
    }

    @Override
    public Page<UserDto> getUsers(String search, Pageable pageable) {
        log.info("Fetching users: search={}, page={}", search, pageable.getPageNumber());
        Page<User> users;
        if (search != null && !search.isBlank()) {
            users = userRepository.findAllByUsernameContainingIgnoreCaseOrFullNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                    search, search, search, pageable);
        } else {
            users = userRepository.findAll(pageable);
        }
        return users.map(this::toDto);
    }

    @Override
    public void deleteUser(Long id) {
        log.info("Deleting user id={}", id);
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found");
        }
        userRepository.deleteById(id);
    }

    private UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.getEmail(),
                user.getRole().name(),
                user.getStatus().name()
        );
    }
}

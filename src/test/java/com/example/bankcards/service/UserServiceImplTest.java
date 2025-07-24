package com.example.bankcards.service;

import com.example.bankcards.dto.UserDto;
import com.example.bankcards.dto.request.UserCreateRequest;
import com.example.bankcards.entity.User;
import com.example.bankcards.enums.Role;
import com.example.bankcards.enums.UserStatus;
import com.example.bankcards.mapper.UserMapper;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    // Используем реальный маппер MapStruct
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Инициализируем вручную, чтобы передать реальный маппер
        userService = new UserServiceImpl(userRepository, passwordEncoder, userMapper);
    }

    @Test
    @DisplayName("Создание пользователя успешно")
    void createUser_success() {
        UserCreateRequest req = new UserCreateRequest("user1", "pass", "User One", "user1@mail.com", "USER");
        when(userRepository.existsByUsername("user1")).thenReturn(false);
        when(userRepository.existsByEmail("user1@mail.com")).thenReturn(false);
        when(passwordEncoder.encode("pass")).thenReturn("encoded");

        User user = User.builder()
                .id(1L)
                .username("user1")
                .password("encoded")
                .fullName("User One")
                .email("user1@mail.com")
                .role(Role.USER)
                .status(UserStatus.ACTIVE)
                .build();
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto dto = userService.createUser(req);
        assertThat(dto.username()).isEqualTo("user1");
        assertThat(dto.role()).isEqualTo("USER");
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Создание пользователя — уже существует")
    void createUser_exists() {
        UserCreateRequest req = new UserCreateRequest("user1", "pass", "User One", "user1@mail.com", "USER");
        when(userRepository.existsByUsername("user1")).thenReturn(true);
        assertThatThrownBy(() -> userService.createUser(req)).isInstanceOf(IllegalArgumentException.class);
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Получение пользователя по id — успешно")
    void getUserById_success() {
        User user = User.builder()
                .id(1L)
                .username("user1")
                .fullName("User One")
                .email("user1@mail.com")
                .role(Role.USER)
                .status(UserStatus.ACTIVE)
                .build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDto dto = userService.getUserById(1L);
        assertThat(dto.id()).isEqualTo(1L);
        assertThat(dto.username()).isEqualTo("user1");
    }

    @Test
    @DisplayName("Получение пользователя по id — не найден")
    void getUserById_notFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userService.getUserById(1L)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Поиск пользователей с фильтром")
    void getUsers_withSearch() {
        User user = User.builder()
                .id(1L)
                .username("user1")
                .fullName("User One")
                .email("user1@mail.com")
                .role(Role.USER)
                .status(UserStatus.ACTIVE)
                .build();
        Page<User> page = new PageImpl<>(List.of(user));
        when(userRepository.findAllByUsernameContainingIgnoreCaseOrFullNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                eq("user"), eq("user"), eq("user"), any(PageRequest.class))).thenReturn(page);

        Page<UserDto> result = userService.getUsers("user", PageRequest.of(0, 10));
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).username()).isEqualTo("user1");
    }

    @Test
    @DisplayName("Поиск пользователей без фильтра")
    void getUsers_noSearch() {
        User user = User.builder()
                .id(1L)
                .username("user1")
                .fullName("User One")
                .email("user1@mail.com")
                .role(Role.USER)
                .status(UserStatus.ACTIVE)
                .build();
        Page<User> page = new PageImpl<>(List.of(user));
        when(userRepository.findAll(any(PageRequest.class))).thenReturn(page);

        Page<UserDto> result = userService.getUsers(null, PageRequest.of(0, 10));
        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    @DisplayName("Удаление пользователя — успешно")
    void deleteUser_success() {
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);
        userService.deleteUser(1L);
        verify(userRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Удаление пользователя — не найден")
    void deleteUser_notFound() {
        when(userRepository.existsById(1L)).thenReturn(false);
        assertThatThrownBy(() -> userService.deleteUser(1L)).isInstanceOf(IllegalArgumentException.class);
    }
}
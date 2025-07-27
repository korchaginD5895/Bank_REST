// src/main/java/com/example/bankcards/mapper/UserMapper.java
package com.example.bankcards.mapper;

import com.example.bankcards.entity.User;
import com.example.bankcards.dto.UserDto;
import com.example.bankcards.dto.request.UserCreateRequest;
import com.example.bankcards.dto.request.UserUpdateRequest;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * Entity → DTO
     */
    UserDto toDto(User user);

    /**
     * Create new entity from create-request
     */
    User toEntity(UserCreateRequest request);

    /**
     * Apply update-request to existing entity
     */
    void updateEntityFromRequest(UserUpdateRequest request, @MappingTarget User user);
}

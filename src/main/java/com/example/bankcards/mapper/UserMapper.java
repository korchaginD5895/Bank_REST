package com.example.bankcards.mapper;

import com.example.bankcards.dto.UserDto;
import com.example.bankcards.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
}
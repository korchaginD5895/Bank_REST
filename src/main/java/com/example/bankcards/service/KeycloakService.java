package com.example.bankcards.service;

import com.example.bankcards.dto.request.UserCreateRequest;
import com.example.bankcards.dto.request.UserUpdateRequest;

public interface KeycloakService {
    String createKeycloakUser(UserCreateRequest rq);
    void updateKeycloakUser(UserUpdateRequest rq);
    void deleteKeycloakUser(String keycloakId);
}

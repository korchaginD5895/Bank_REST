package com.example.bankcards.exception;

/**
 * Thrown when an error occurs during communication with Keycloak.
 */
public class KeycloakServiceException extends RuntimeException {

    public KeycloakServiceException(String message) {
        super(message);
    }

    public KeycloakServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

// src/main/java/com/example/bankcards/service/impl/KeycloakServiceImpl.java
package com.example.bankcards.service.impl;

import com.example.bankcards.dto.request.UserCreateRequest;
import com.example.bankcards.dto.request.UserUpdateRequest;
import com.example.bankcards.exception.KeycloakServiceException;
import com.example.bankcards.service.KeycloakService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Response;
import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeycloakServiceImpl implements KeycloakService {

    private final Keycloak keycloakAdminClient;

    @Value("${keycloak.realm}")
    private String realm;

    @Override
    public String createKeycloakUser(UserCreateRequest rq) {
        log.info("Creating Keycloak user '{}' with role '{}'", rq.username(), rq.role());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(rq.username());
        user.setEnabled(true);
        user.setRealmRoles(Collections.singletonList(rq.role().name()));

        CredentialRepresentation cred = new CredentialRepresentation();
        cred.setType(CredentialRepresentation.PASSWORD);
        cred.setTemporary(false);
        cred.setValue(rq.password());
        user.setCredentials(Collections.singletonList(cred));

        try (Response resp = keycloakAdminClient.realm(realm).users().create(user)) {
            int status = resp.getStatus();
            if (status != Response.Status.CREATED.getStatusCode()) {
                log.error("Failed to create Keycloak user '{}', status: {}", rq.username(), status);
                throw new KeycloakServiceException(
                        String.format("Keycloak create user failed: HTTP %d", status)
                );
            }
            String location = resp.getHeaderString("Location");
            String kcId = location.substring(location.lastIndexOf('/') + 1);
            log.debug("Keycloak user created: username='{}', id='{}'", rq.username(), kcId);
            return kcId;
        } catch (ProcessingException e) {
            log.error("Error creating Keycloak user '{}'", rq.username(), e);
            throw new KeycloakServiceException(
                    String.format("Error creating Keycloak user '%s'", rq.username()), e
            );
        }
    }

    @Override
    public void updateKeycloakUser(UserUpdateRequest rq) {
        log.info("Updating Keycloak user id='{}'", rq.id());
        var resource = keycloakAdminClient.realm(realm).users().get(rq.id().toString());

        if (rq.password() != null && !rq.password().isBlank()) {
            log.debug("Resetting password for Keycloak user id='{}'", rq.id());
            CredentialRepresentation cred = new CredentialRepresentation();
            cred.setType(CredentialRepresentation.PASSWORD);
            cred.setTemporary(false);
            cred.setValue(rq.password());
            resource.resetPassword(cred);
        }

        if (rq.role() != null) {
            log.debug("Updating roles for Keycloak user id='{}' to '{}'", rq.id(), rq.role());
            UserRepresentation user = resource.toRepresentation();
            user.setRealmRoles(Collections.singletonList(rq.role().name()));
            resource.update(user);
        }

        log.debug("Keycloak user updated: id='{}'", rq.id());
    }

    @Override
    public void deleteKeycloakUser(String keycloakId) {
        log.info("Deleting Keycloak user id='{}'", keycloakId);
        keycloakAdminClient.realm(realm)
                .users()
                .get(keycloakId)
                .remove();
        log.debug("Keycloak user deleted: id='{}'", keycloakId);
    }
}

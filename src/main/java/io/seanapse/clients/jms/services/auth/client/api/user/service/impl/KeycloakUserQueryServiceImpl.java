package io.seanapse.clients.jms.services.auth.client.api.user.service.impl;

import io.seanapse.clients.jms.services.auth.client.api.user.service.UserQueryService;
import io.seanapse.clients.jms.services.auth.client.api.infrastructure.client.KeycloakAdminClient;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class KeycloakUserQueryServiceImpl implements UserQueryService {
    private final KeycloakAdminClient keycloakAdminClient;

    @Autowired
    public KeycloakUserQueryServiceImpl(KeycloakAdminClient keycloakAdminClient) {
        this.keycloakAdminClient = keycloakAdminClient;
    }

    @Override
    public List<UserRepresentation> getAllUsers() {
        UsersResource usersResource = this.getRealmRources().users();

        return usersResource.list();
    }

    @Override
    public List<UserRepresentation> searchInUsers(String keyword) {
        return this.getRealmRources().users().search(keyword);
    }

    @Override
    public UserRepresentation getUserById(String id) {
        return this.getRealmRources().users().get(id).toRepresentation();
    }

    @Override
    public List<GroupRepresentation> getGroups() {
        return this.getRealmRources().groups().groups();
    }

    @Override
    public List<UserRepresentation> getUsersByGroup(String groupId) {
        return this.getRealmRources().groups().group(groupId).members();
    }

    private RealmResource getRealmRources() {
        Keycloak keycloak = keycloakAdminClient.getAdminClient();
        keycloak.tokenManager().getAccessToken();

        return keycloak.realm(keycloakAdminClient.getChildRealm());
    }
}

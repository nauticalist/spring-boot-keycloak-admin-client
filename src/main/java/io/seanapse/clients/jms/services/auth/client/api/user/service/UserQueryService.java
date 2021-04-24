package io.seanapse.clients.jms.services.auth.client.api.user.service;

import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public interface UserQueryService {
    List<UserRepresentation> getAllUsers();

    List<UserRepresentation> searchInUsers(String keyword);

    UserRepresentation getUserById(String userId);

    List<GroupRepresentation> getGroups();

    List<UserRepresentation> getUsersByGroup(String group);
}

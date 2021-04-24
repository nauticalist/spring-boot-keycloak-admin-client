package io.seanapse.clients.jms.services.auth.client.api.user.controller;

import io.seanapse.clients.jms.services.auth.client.api.user.service.UserQueryService;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/groups")
public class GroupsController {
    private final UserQueryService userQueryService;

    @Autowired
    public GroupsController(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_AUTHSERVER_USER')")
    public ResponseEntity<List<GroupRepresentation>> getGroups() {
        return new ResponseEntity<>(userQueryService.getGroups(), HttpStatus.OK);

    }

    @GetMapping("/{groupId}/members")
    @PreAuthorize("hasAnyRole('ROLE_AUTHSERVER_USER')")
    public ResponseEntity<List<UserRepresentation>> getUsersByGroup(@PathVariable(value = "groupId") String groupId) {
        return new ResponseEntity<>(userQueryService.getUsersByGroup(groupId), HttpStatus.OK);

    }
}

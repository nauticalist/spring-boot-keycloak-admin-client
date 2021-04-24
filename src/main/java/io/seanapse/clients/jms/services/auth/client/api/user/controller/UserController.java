package io.seanapse.clients.jms.services.auth.client.api.user.controller;

import io.seanapse.clients.jms.services.auth.client.api.user.service.UserQueryService;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {
    private final UserQueryService userQueryService;

    @Autowired
    public UserController(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_AUTHSERVER_USER')")
    public ResponseEntity<List<UserRepresentation>> getAllUsers() {
        return new ResponseEntity<>(userQueryService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping(path = "/search")
    @PreAuthorize("hasAnyRole('ROLE_AUTHSERVER_USER')")
    public ResponseEntity<List<UserRepresentation>> searchInUsers(@RequestParam(value = "q") String keyword) {
        return new ResponseEntity<>(userQueryService.searchInUsers(keyword), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_AUTHSERVER_USER')")
    public ResponseEntity<UserRepresentation> getUserById(@PathVariable(value = "id") String id) {
        return new ResponseEntity<>(userQueryService.getUserById(id), HttpStatus.OK);
    }
}

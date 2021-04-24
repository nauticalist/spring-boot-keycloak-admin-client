package io.seanapse.clients.jms.services.auth.client.api.infrastructure.client;

import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KeycloakAdminClient {
    private Keycloak keycloak = null;

    @Value("${admin.auth-server-url}")
    private String authServerUrl;

    @Value("${admin.keycloak.masterRealm}")
    private String realm;

    @Value("${admin.keycloak.realm}")
    private String childRealm;

    @Value("${admin.keycloak.clientId}")
    private String clientId;

    @Value("${admin.keycloak.username}")
    private String username;

    @Value("${admin.keycloak.password}")
    private String password;

    public String getRealm() {
        return realm;
    }

    public String getChildRealm() {
        return childRealm;
    }

    public String getClientId() {
        return clientId;
    }

    public Keycloak getAdminClient(){
        if(keycloak == null){
            keycloak = KeycloakBuilder.builder()
                    .serverUrl(authServerUrl)
                    .grantType(OAuth2Constants.PASSWORD)
                    .realm(realm)
                    .clientId(clientId)
                    .username(username)
                    .password(password)
                    .resteasyClient(new ResteasyClientBuilder()
                            .connectionPoolSize(10)
                            .build())
                    .build();

            log.info("[REALM] : " + realm);
        }
        return keycloak;
    }
}
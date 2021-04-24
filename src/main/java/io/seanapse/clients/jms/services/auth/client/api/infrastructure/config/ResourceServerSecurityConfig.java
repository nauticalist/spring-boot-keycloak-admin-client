package io.seanapse.clients.jms.services.auth.client.api.infrastructure.config;


import io.seanapse.clients.jms.services.auth.client.api.infrastructure.security.KeycloakRoleConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableWebSecurity
public class ResourceServerSecurityConfig extends WebSecurityConfigurerAdapter {
    private final KeycloakRoleConverter keycloakRoleConverter;
    private final OAuth2ResourceServerProperties resourceServerProperties;

    @Autowired
    public ResourceServerSecurityConfig(KeycloakRoleConverter keycloakRoleConverter, OAuth2ResourceServerProperties resourceServerProperties) {
        this.keycloakRoleConverter = keycloakRoleConverter;
        this.resourceServerProperties = resourceServerProperties;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer().jwt()
                .decoder(jwtDecoder())
                .jwtAuthenticationConverter(jwtAuthenticationConverter());
    }

    @Bean
    protected JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(keycloakRoleConverter);
        return jwtAuthenticationConverter;
    }

    @Bean
    protected JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(this.resourceServerProperties.getJwt().getJwkSetUri()).build();
    }
}

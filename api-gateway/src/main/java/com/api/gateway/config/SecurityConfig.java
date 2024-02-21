package com.api.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoders;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.net.http.HttpClient;


@EnableWebFluxSecurity
public class SecurityConfig {


    @Bean
    public SecurityWebFilterChain springSecurityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity.authorizeExchange(exchange -> exchange
                        .pathMatchers("/eureka/**")
                        .permitAll()
                        .anyExchange()
                        .authenticated())
                        .oauth2ResourceServer( oauth2 -> oauth2
                        .jwt(Customizer.withDefaults())
                );

        return serverHttpSecurity.build();
    }
    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        return NimbusReactiveJwtDecoder.withIssuerLocation("http://localhost:8081/realms/spring-boot-microservices-realm").build();
    }

}



/*
                        oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtDecoder(jwtDecoder())
                        )
*/

/*@Bean
    public ReactiveJwtDecoder jwtDecoder() {
        return ReactiveJwtDecoders.fromOidcIssuerLocation("http://localhost:8080/realms/spring-boot-microservices-realm/"); //Aqui va el uri del issuer
    }*/

/*
spring.security.oauth2.resourceserver.jwt.issuer-uri=http://localhost:8080/realms/spring-boot-microservices-realm
spring.security.oauth2.resourceserver.jwt.jwk-set-uri=http://localhost:8080/realms/spring-boot-microservices-realm/protocol/openid-connect/certs

spring.security.oauth2.client.registration.okta.client-id=spring-cloud-client
spring.security.oauth2.client.registration.okta.client-secret= 2BiXwmsFB1MD6gr7NZLUY5ahJm6jkF7x
spring.security.oauth2.client.registration.okta.scope=openid,offline_access
spring.security.oauth2.client.registration.okta.authorization-grant-type=client_credentials


spring.main.allow-bean-definition-overriding=true
server.ssl.enabled=false


 */

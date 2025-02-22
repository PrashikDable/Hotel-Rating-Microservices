package com.emicro.ApiGateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {

//        httpSecurity
//                .authorizeExchange()
//                .anyExchange()
//                .authenticated()
//                .and()
//                .oauth2Client()
//                .and()
//                .oauth2ResourceServer()
//                .jwt();

        httpSecurity
                .authorizeExchange(exchanges -> exchanges
                        .anyExchange().authenticated()
                )
                .oauth2Client(Customizer.withDefaults())  // Enables OAuth2 client
                .oauth2ResourceServer(oAuth2 -> oAuth2
                        .jwt(Customizer.withDefaults()));

        return httpSecurity.build();
        }

    }
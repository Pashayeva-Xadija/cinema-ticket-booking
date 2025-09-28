package com.example.apigateway.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerGroupsConfig {
    @Bean GroupedOpenApi auth()        { return GroupedOpenApi.builder().group("auth").pathsToMatch("/api/auth/**").build(); }
    @Bean GroupedOpenApi booking()     { return GroupedOpenApi.builder().group("booking").pathsToMatch("/api/booking/**").build(); }
    @Bean GroupedOpenApi inventory()   { return GroupedOpenApi.builder().group("inventory").pathsToMatch("/api/inventory/**").build(); }
    @Bean GroupedOpenApi notification(){ return GroupedOpenApi.builder().group("notification").pathsToMatch("/api/notification/**").build(); }
    @Bean GroupedOpenApi payment()     { return GroupedOpenApi.builder().group("payment").pathsToMatch("/api/payment/**").build(); }
    @Bean GroupedOpenApi showtime()    { return GroupedOpenApi.builder().group("showtime").pathsToMatch("/api/showtime/**").build(); }
    @Bean GroupedOpenApi ticket()      { return GroupedOpenApi.builder().group("ticket").pathsToMatch("/api/ticket/**").build(); }
}

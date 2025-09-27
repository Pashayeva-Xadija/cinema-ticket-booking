package com.example.notificationservice.config;



import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(servers = {
        @Server(url = "/api/notification", description = "via API Gateway")
})
@Configuration
public class OpenApiServerConfig { }


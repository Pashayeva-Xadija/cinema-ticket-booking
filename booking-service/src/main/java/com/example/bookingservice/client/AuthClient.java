package com.example.bookingservice.client;
import com.example.bookingservice.dto.UserEmailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "auth-client", url = "${clients.auth.base-url}")
public interface AuthClient {
    @GetMapping("/auth/users/{id}/email")
    UserEmailResponse getEmail(@PathVariable("id") Long id);
}





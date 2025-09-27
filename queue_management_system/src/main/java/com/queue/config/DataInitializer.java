package com.queue.config;

import com.queue.enums.Role;
import com.queue.model.User;
import com.queue.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final PasswordEncoder encoder;

    @Bean
    CommandLineRunner initUsers(UserRepository repo){
        return args -> {
            if (repo.count() == 0) {
                repo.save(User.builder().username("admin").password(encoder.encode("admin")).role(Role.ADMIN).build());
                repo.save(User.builder().username("op").password(encoder.encode("op")).role(Role.OPERATOR).build());
            }
        };
    }
}

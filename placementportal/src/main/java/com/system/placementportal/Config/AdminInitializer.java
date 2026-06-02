package com.system.placementportal.Config;

import com.system.placementportal.Entity.Role;
import com.system.placementportal.Entity.User;
import com.system.placementportal.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AdminInitializer {

    private final UserRepository userRepository;

    @Bean
    public CommandLineRunner createAdmin() {
        return args -> {

            if (userRepository.findByEmail("admin@gmail.com").isEmpty()) {

                User admin = User.builder()
                        .email("admin@gmail.com")
                        .password("admin123")
                        .role(Role.ADMIN)
                        .build();

                userRepository.save(admin);

                System.out.println("Admin created");
            }
        };
    }
}
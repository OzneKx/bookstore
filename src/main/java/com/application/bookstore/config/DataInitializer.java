package com.application.bookstore.config;

import com.application.bookstore.data.entity.User;
import com.application.bookstore.data.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner init(UserRepository userRepository, PasswordEncoder encoder) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                createUserWithRole("admin", encoder, "123456", "ROLE_ADMIN", userRepository);
            }

            if (userRepository.findByUsername("user").isEmpty()) {
                createUserWithRole("user", encoder, "654321", "ROLE_USER", userRepository);
            }
        };
    }

    private void createUserWithRole(String username, PasswordEncoder encoder, String password, String role,
                          UserRepository userRepository) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setRole(role);
        userRepository.save(user);

        System.out.println("User created: (username: " + username + " | password: " + password );
    }
}

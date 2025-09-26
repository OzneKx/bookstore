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
                User user = new User();
                user.setUsername("admin");
                user.setPassword(encoder.encode("123456"));
                user.setRole("ROLE_ADMIN");
                userRepository.save(user);

                System.out.println("User admin created: (username: admin | password: 123456)");
            }
        };
    }
}

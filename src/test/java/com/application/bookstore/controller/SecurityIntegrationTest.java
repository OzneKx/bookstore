package com.application.bookstore.controller;

import com.application.bookstore.dto.AuthRequest;
import com.application.bookstore.dto.AuthResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SecurityIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private String userToken;
    private String adminToken;

    @BeforeEach
    void setUp() {
        userToken = loginCredentialsForTesting("user");
        adminToken = loginCredentialsForTesting("admin");
    }

    private String loginCredentialsForTesting(String username) {
        ResponseEntity<AuthResponse> response = restTemplate.postForEntity(
                "/auth/login",
                new AuthRequest(username, "123456"),
                AuthResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getToken());

        return response.getBody().getToken();
    }

    private Long createBook(String token) {
        Map<String, Object> request = Map.of(
                "title", "Test Book",
                "author", "Kenzo",
                "publishYear", 2025,
                "languages", new String[]{"PT"},
                "publisher", "PENGUIN_RANDOM_HOUSE"
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                "/api/books",
                entity,
                Map.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());

        Number idNumber = (Number) response.getBody().get("id");
        return idNumber.longValue();
    }

    @Test
    void deleteBookShouldBeForbiddenForNormalUser() {
        Long bookId = createBook(adminToken);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(userToken);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                "/api/books/" + bookId,
                HttpMethod.DELETE,
                entity,
                Map.class
        );

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void deleteBookShouldBeAccessibleForAdmin() {
        Long bookId = createBook(adminToken);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(adminToken);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                "/api/books/" + bookId,
                HttpMethod.DELETE,
                entity,
                Void.class
        );

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}

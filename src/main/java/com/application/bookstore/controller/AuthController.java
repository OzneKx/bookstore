package com.application.bookstore.controller;

import com.application.bookstore.dto.AuthRequest;
import com.application.bookstore.dto.AuthResponse;
import com.application.bookstore.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "Authentication", description = "Endpoints for user authentication and JWT generation")
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Operation(
            summary = "Authenticate user and generate JWT",
            description = "Provide username and password to receive a JWT token"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful authentication",
            content = @Content(schema = @Schema(implementation = AuthResponse.class))
    )
    @ApiResponse(
            responseCode = "401",
            description = "Invalid credentials"
    )
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateAndGenerateToken(
            @Valid @RequestBody @Parameter(
                    description = "Credentials required to authenticate and obtain a JWT token")
            AuthRequest authRequest) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        String jwt = jwtUtil.generateToken((UserDetails) auth.getPrincipal());
        return ResponseEntity.ok(new AuthResponse(jwt));
    }
}

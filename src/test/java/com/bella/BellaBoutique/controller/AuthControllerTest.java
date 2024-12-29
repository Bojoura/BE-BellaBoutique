package com.bella.BellaBoutique.controller;

import com.bella.BellaBoutique.DTO.AuthenticationInputDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void authenticated_ShouldHandleNullPrincipal() {
        // Act
        ResponseEntity<Object> response = authController.authenticated(null, null);

        // Assert
        assertEquals(ResponseEntity.ok().body(null), response);
    }

    @Test
    void authenticated_ShouldReturnPrincipal() {
        // Arrange
        Principal principal = mock(Principal.class);

        // Act
        ResponseEntity<Object> response = authController.authenticated(null, principal);

        // Assert
        assertEquals(ResponseEntity.ok().body(principal), response);
    }

    @Test
    void createAuthenticationToken_ShouldReturnUnauthorizedOnBadCredentials() {
        // Arrange
        AuthenticationInputDto inputDto = new AuthenticationInputDto("test@example.com", "wrongPassword");

        doThrow(new BadCredentialsException("Bad credentials"))
                .when(authenticationManager)
                .authenticate(new UsernamePasswordAuthenticationToken(inputDto.getEmail(), inputDto.getPassword()));

        // Act
        ResponseEntity<?> response = authController.createAuthenticationToken(inputDto);

        // Assert
        assertEquals(ResponseEntity.status(401).body("Incorrect email of wachtwoord"), response);
    }

    @Test
    void createAuthenticationToken_ShouldReturnNotFoundOnUsernameNotFoundException() {
        // Arrange
        AuthenticationInputDto inputDto = new AuthenticationInputDto("nonexistent@example.com", "password123");

        doThrow(new UsernameNotFoundException("User not found"))
                .when(authenticationManager)
                .authenticate(new UsernamePasswordAuthenticationToken(inputDto.getEmail(), inputDto.getPassword()));

        // Act
        ResponseEntity<?> response = authController.createAuthenticationToken(inputDto);

        // Assert
        assertEquals(ResponseEntity.status(404).body("Email niet gevonden"), response);
    }

    @Test
    void createAuthenticationToken_ShouldReturnInternalServerErrorOnException() {
        // Arrange
        AuthenticationInputDto inputDto = new AuthenticationInputDto("test@example.com", "password123");

        doThrow(new RuntimeException("Unexpected error"))
                .when(authenticationManager)
                .authenticate(new UsernamePasswordAuthenticationToken(inputDto.getEmail(), inputDto.getPassword()));

        // Act
        ResponseEntity<?> response = authController.createAuthenticationToken(inputDto);

        // Assert
        assertEquals(ResponseEntity.status(500).body("Er is een fout opgetreden tijdens het verwerken van uw verzoek"), response);
    }

    @Test
    void register_ShouldThrowExceptionForNullUser() {
        // Act & Assert
        Exception exception = assertThrows(NullPointerException.class, () -> authController.register(null));
        assertNotNull(exception);
    }
}

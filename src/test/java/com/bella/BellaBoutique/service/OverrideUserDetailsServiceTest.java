package com.bella.BellaBoutique.service;

import com.bella.BellaBoutique.exception.UserNotFoundException;
import com.bella.BellaBoutique.model.users.Authority;
import com.bella.BellaBoutique.model.users.User;
import com.bella.BellaBoutique.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OverrideUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OverrideUserDetailsService overrideUserDetailsService;

    @Test
    void loadUserByUsername_ShouldReturnUserDetails_WhenUserExists() {
        // Arrange
        String email = "test@example.com";
        String username = "testuser";
        String password = "password123";

        Authority authority = new Authority("ROLE_USER");

        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        user.setAuthorities(Set.of(authority));

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // Act
        UserDetails userDetails = overrideUserDetailsService.loadUserByUsername(email);

        // Assert
        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));

        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void loadUserByUsername_ShouldThrowUserNotFoundException_WhenUserDoesNotExist() {
        // Arrange
        String email = "notfound@example.com";

        // Act & Assert
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () ->
                overrideUserDetailsService.loadUserByUsername(email));

        assertTrue(exception.getMessage().contains("Gebruiker niet gevonden met email: " + email));
    }



    @Test
    void loadUserByUsername_ShouldThrowUsernameNotFoundException_WhenUserEmailIsNull() {
        // Arrange
        String email = null;

        // Act & Assert
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () ->
                overrideUserDetailsService.loadUserByUsername(email));

        assertEquals("Email mag niet null zijn.", exception.getMessage());
    }

}

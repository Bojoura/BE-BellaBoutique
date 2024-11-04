package com.bella.BellaBoutique.controller;

import com.bella.BellaBoutique.DTO.AuthenticationInputDto;
import com.bella.BellaBoutique.DTO.AuthenticationOutputDto;
import com.bella.BellaBoutique.model.users.User;
import com.bella.BellaBoutique.repository.UserRepository;
import com.bella.BellaBoutique.service.OverrideUserDetailsService;
import com.bella.BellaBoutique.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@CrossOrigin
@RestController
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final OverrideUserDetailsService userDetailsService;

    private final JwtUtil jwtUtl;

    public AuthController(AuthenticationManager authenticationManager, OverrideUserDetailsService userDetailsService, JwtUtil jwtUtl) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtl = jwtUtl;
    }

    @GetMapping(value = "/authenticated")
    public ResponseEntity<Object> authenticated(Authentication authentication, Principal principal) {
        return ResponseEntity.ok().body(principal);
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationInputDto authenticationInputDto) {
        String email = authenticationInputDto.getEmail();
        String password = authenticationInputDto.getPassword();

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect email of wachtwoord");
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email niet gevonden");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Er is een fout opgetreden tijdens het verwerken van uw verzoek");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        final String jwt = jwtUtl.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationOutputDto(jwt));
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return "Email is al in gebruik!";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "Registratie succesvol!";
    }
}

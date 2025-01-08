package com.bella.BellaBoutique.service;

import com.bella.BellaBoutique.exception.UserNotFoundException;
import com.bella.BellaBoutique.model.users.Authority;
import com.bella.BellaBoutique.model.users.User;
import com.bella.BellaBoutique.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class OverrideUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public OverrideUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Loading User By Username: " + email);
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UserNotFoundException("Gebruiker niet gevonden met email: " + email);
        }
        User foundUser = user.get();

        String username = foundUser.getUsername();
        String password = foundUser.getPassword();
        System.out.println("Gebruikersnaam: " + username);

        Set<Authority> authorities = foundUser.getAuthorities();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Authority authority : authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
        }
        System.out.println("Authorities: " + grantedAuthorities);
        return new org.springframework.security.core.userdetails.User(email, password, grantedAuthorities);
    }

}

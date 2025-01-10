package com.bella.BellaBoutique.config;

import com.bella.BellaBoutique.filter.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter, UserDetailsService userDetailsService, CorsConfigurationSource corsConfigurationSource) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        var auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(passwordEncoder);
        auth.setUserDetailsService(userDetailsService);
        return new ProviderManager(auth);
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .cors(Customizer.withDefaults())
                .httpBasic(basic -> basic.disable())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Openbare endpoints
                        .requestMatchers("/authenticate").permitAll()
                        .requestMatchers("/resources/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/products").permitAll()
                        .requestMatchers(HttpMethod.GET, "/products/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/categories/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/reviews/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/cart/**").permitAll()

                        // Gebruikerstoegang endpoints
                        .requestMatchers(HttpMethod.GET, "/users").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/user").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/*").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/users/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/users/{email}", "/users/{email}/authorities").hasAnyRole("CUSTOMER", "EMPLOYEE", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/users/{email}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/{email}/authorities").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/users/{email}/authorities").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/users/{email}").hasRole("ADMIN")

                        // Review endpoints
                        .requestMatchers("/review/user/**").hasAnyRole("CUSTOMER", "EMPLOYEE", "ADMIN")
                        .requestMatchers("/review/admin/**").hasRole("ADMIN")

                        // Order endpoints
                        .requestMatchers(HttpMethod.POST, "/orders/user").hasAnyRole("CUSTOMER", "EMPLOYEE", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/orders/user").hasAnyRole("CUSTOMER", "EMPLOYEE", "ADMIN")
                        .requestMatchers("/orders/user/**").hasAnyRole("CUSTOMER", "EMPLOYEE", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/orders/admin").hasRole("ADMIN")
                        .requestMatchers("/orders/admin/**").hasRole("ADMIN")

                        // Default rule: alle andere endpoints blokkeren
                        .requestMatchers("/authenticated").authenticated()
                        .anyRequest().denyAll()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}

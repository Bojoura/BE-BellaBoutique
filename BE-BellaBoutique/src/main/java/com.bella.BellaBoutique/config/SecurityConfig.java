package com.bella.BellaBoutique.config;

import com.bella.BellaBoutique.filter.JwtRequestFilter;
import com.bella.BellaBoutique.service.OverrideUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public final OverrideUserDetailsService overrideUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(OverrideUserDetailsService overrideUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.overrideUserDetailsService = overrideUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    protected SecurityFilterChain filter(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .httpBasic(basic -> basic.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(HttpMethod.GET, "/api/products", "/api/products/{productId}", "api/products/{productId}/images").permitAll()
                                .requestMatchers(HttpMethod.POST, "/products/{productId}/images").hasAnyRole("USER", "EMPLOYEE", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/products/{productId}/reviews").permitAll()
                                .requestMatchers("/review/user/**").hasAnyRole("USER", "EMPLOYEE", "ADMIN")
                                .requestMatchers("/review/admin/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/orders/user").hasAnyRole("USER", "EMPLOYEE", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/orders/user").hasAnyRole("USER", "EMPLOYEE", "ADMIN")
                                .requestMatchers("/orders/user/**").hasAnyRole("USER", "EMPLOYEE", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/orders/admin").hasRole("ADMIN")
                                .requestMatchers("/orders/admin/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/users/user").permitAll()
                                .requestMatchers(HttpMethod.GET, "/users/*").permitAll()
                                .requestMatchers(HttpMethod.POST, "/users/*").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/users/*").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/users/*").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/users/{username}", "/users/{username}/authorities").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/users/{username}/authorities").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/users/{username}/authorities").hasRole("ADMIN")
                                .requestMatchers("/authenticated").authenticated()
                                .requestMatchers("/authenticate").permitAll()
                                .anyRequest().denyAll()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
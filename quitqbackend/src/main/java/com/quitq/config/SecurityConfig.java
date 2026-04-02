package com.quitq.config;

import com.quitq.security.JwtAuthFilter;
import com.quitq.security.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize

                        // public routes
                        .requestMatchers(HttpMethod.POST, "/users/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()

                        // product GET - public
                        .requestMatchers(HttpMethod.GET, "/products").permitAll()
                        .requestMatchers(HttpMethod.GET, "/products/**").permitAll()


                        // product POST/PUT/DELETE - seller only
                        .requestMatchers(HttpMethod.POST, "/products/add").hasAuthority("SELLER")
                        .requestMatchers(HttpMethod.PUT, "/products/**").hasAuthority("SELLER")
                        .requestMatchers(HttpMethod.DELETE, "/products/**").hasAuthority("SELLER")

                        // category GET - public
                        .requestMatchers(HttpMethod.GET, "/categories/**").permitAll()

                        // category POST/PUT/DELETE - authenticated
                        .requestMatchers(HttpMethod.POST, "/categories/add").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/categories/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/categories/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/users/get-all").hasAuthority("ADMIN")

//                        cart routes to be secure
                                // cart routes - needs token
                                .requestMatchers(HttpMethod.GET, "/cart/**").authenticated()
                                .requestMatchers(HttpMethod.POST, "/cart/add").authenticated()
                                .requestMatchers(HttpMethod.PUT, "/cart/**").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/cart/**").authenticated()
//                        order routes to be secure
                                // order routes - needs token
                                .requestMatchers(HttpMethod.POST, "/orders/place").authenticated()
                                .requestMatchers(HttpMethod.GET, "/orders/**").authenticated()
                                .requestMatchers(HttpMethod.PUT, "/orders/**").authenticated()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
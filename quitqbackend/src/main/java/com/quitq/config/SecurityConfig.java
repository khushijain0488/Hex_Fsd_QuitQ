package com.quitq.config;

import com.quitq.security.JwtAuthFilter;
import com.quitq.security.UserDetailsServiceImpl;
import com.quitq.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration

public class SecurityConfig {


    private final JwtAuthFilter jwtAuthFilter;
private final UserService userService;
    public SecurityConfig(JwtAuthFilter jwtAuthFilter, @Lazy UserService userService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userService = userService;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize

                        // public routes
                        .requestMatchers(HttpMethod.POST, "/users/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/auth/login").authenticated()
                                .requestMatchers("/uploads/**").permitAll()
                        // product GET - public
//                        .requestMatchers(HttpMethod.GET, "/products").permitAll()
                                .requestMatchers(HttpMethod.GET, "/products/v1/get").permitAll()
                        .requestMatchers(HttpMethod.GET, "/products/get-by-id/**").permitAll()

                                .requestMatchers(HttpMethod.GET,"/users/api/v2/loggedInUser").authenticated()
                                .requestMatchers(HttpMethod.GET,"/users/api/v1/getUserById/**").authenticated()
                        // product POST/PUT/DELETE - seller only
                        .requestMatchers(HttpMethod.POST, "/products/add").hasAuthority("SELLER")
                        .requestMatchers(HttpMethod.PUT, "/products/**").hasAuthority("SELLER")
                        .requestMatchers(HttpMethod.DELETE, "/products/**").hasAuthority("SELLER")
                                .requestMatchers(HttpMethod.GET,"/products/get-product").hasAuthority("SELLER")
                                .requestMatchers(HttpMethod.GET,"/products/upload-image").hasAuthority("SELLER")
                        // category GET - public
                        .requestMatchers(HttpMethod.GET, "/categories/**").permitAll()

                        // category POST/PUT/DELETE - authenticated
                        .requestMatchers(HttpMethod.POST, "/categories/add").authenticated()
                                .requestMatchers(HttpMethod.GET,"/categories/get-all").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/categories/update/**").authenticated()
                                .requestMatchers(HttpMethod.GET,"/categories/get/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/categories/delete/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/users/get-all").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET,"/admin/get-all-user").hasAuthority("ADMIN")
//                        cart routes to be secure
                                // cart routes - needs token
                                .requestMatchers(HttpMethod.GET,"/cart/api/v1/get/**").authenticated()
                                .requestMatchers(HttpMethod.GET, "/cart/api/v2/get").authenticated()
                                .requestMatchers(HttpMethod.POST, "/cart/add").authenticated()
                                .requestMatchers(HttpMethod.DELETE,"/cart/remove/**").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/cart/clear").authenticated()
                                .requestMatchers(HttpMethod.PUT,"/cart/update/**").authenticated()
//                        order routes to be secure
                                // order routes - needs token
                                .requestMatchers(HttpMethod.POST, "/orders/place").authenticated()
                                .requestMatchers(HttpMethod.GET,"/orders/getById/**").authenticated()
                                .requestMatchers(HttpMethod.GET, "/orders/getAllOrdersOfUser").authenticated()
                                .requestMatchers(HttpMethod.PUT, "/orders/**").authenticated()
                                .requestMatchers(HttpMethod.GET,"/orders/v1/getAll").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET,"/orders/v2/getAll").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET,"/orders/api/v1/seller/{sellerId}").hasAuthority("SELLER")
                                .requestMatchers(HttpMethod.GET,"/orders/api/v2/loggedInseller").hasAuthority("SELLER")
                                .requestMatchers(HttpMethod.PUT,"/orders/status/**").hasAuthority("SELLER")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
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
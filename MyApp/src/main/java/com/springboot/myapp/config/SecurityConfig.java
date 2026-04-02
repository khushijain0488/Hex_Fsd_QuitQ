package com.springboot.myapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService users() {
        UserDetails customer1 = User.builder()
                .username("harry")
                .password("{noop}potter")
                .authorities("CUSTOMER")
                .build();
        UserDetails customer2 = User.builder()
                .username("ronald")
                .password("{noop}weasley")
                .authorities("CUSTOMER")
                .build();
        UserDetails executive1 = User.builder()
                .username("hermione")
                .password("{noop}granger")
                .authorities("EXECUTIVE")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}admin")
                .authorities("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(customer1,customer2,executive1, admin);
    }

    @Bean
    public SecurityFilterChain bankingSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.OPTIONS,"/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/ticket/get-all")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/ticket/get/{id}")
                        .authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/ticket/customer/{customerId}/v1")
                        .hasAnyRole("CUSTOMER")
                        .requestMatchers(HttpMethod.POST, "/api/ticket/add/{customerId}")
                        .hasAnyAuthority("ADMIN","CUSTOMER")
                        .requestMatchers(HttpMethod.PUT, "/api/ticket/assign-executive/{ticketId}/{executiveId}")
                        .hasAuthority("ADMIN")
                        .anyRequest().authenticated()
                );
        http.httpBasic(Customizer.withDefaults());  //Spring understand that i am using this technique
        return http.build();
    }
}
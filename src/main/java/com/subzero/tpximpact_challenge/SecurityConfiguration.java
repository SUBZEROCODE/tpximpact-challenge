package com.subzero.tpximpact_challenge;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/url-shortener/health").permitAll()
                .requestMatchers("/api/v1/url-shortener/{alias}").permitAll()
                .requestMatchers("/api/v1/url-shortener/shorten").permitAll()
                .requestMatchers("/api/v1/url-shortener/urls").permitAll()
                .anyRequest().permitAll()
            );
        return http.build();
    }

    @Bean
    UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "DELETE"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/v1/url-shortener/**", configuration);
        return source;
    }


    // @Bean
    // public CorsConfigurationSource corsConfigurationSource() {
    //     CorsConfiguration configuration = new CorsConfiguration();
    //     configuration.setAllowedOrigins(List.of("http://localhost")); // Adjust as needed
    //     configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
    //     configuration.setAllowedHeaders(List.of("*"));
    //     configuration.setAllowCredentials(true);
        
    //     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    //     source.registerCorsConfiguration("/**", configuration);
    //     return source;
    // }

    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //     http
    //         .cors(cors -> cors.configurationSource(corsConfigurationSource()))
    //         .csrf(csrf -> csrf.disable())
    //         .authorizeHttpRequests(auth -> auth
    //             // .requestMatchers("/api/v1/url-shortener/**").permitAll()
    //             .anyRequest().permitAll()
    //     );
    //     return http.build();

    // }
}


package com.shangrila.aptmaintenance.config;

import com.shangrila.aptmaintenance.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // ✅ KEY
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/error", "/favicon.ico","/index.html",
                                "/static/**",
                                "/*.js",
                                "/**/*.js",
                                "/**/*.css",
                                "/*.css",
                                "/*.png",
                                "/*.ico",
                                "/manifest.json"
                                ).permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(
                                "/dashboard",
                                "/expenses",
                                "/login",
                                "/error"
                        ).permitAll()

                        // 🔥 Allow BOTH to view
                        .requestMatchers(HttpMethod.GET, "/api/expenses/**")
                        .hasAnyRole("ADMIN", "RESIDENT")

                        // 🔥 Only ADMIN can modify
                        .requestMatchers(HttpMethod.POST, "/api/expenses/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/api/expenses/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/api/expenses/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/payments/**")
                        .hasRole("ADMIN")

                        .anyRequest().authenticated()
                )/*.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)*/;


        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of("http://localhost:3000",
                "https://apartment-maintenance-eight.vercel.app"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}

package com.shangrila.aptmaintenance.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.Collections;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**@Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String path = request.getRequestURI();

        // ✅ Allow auth endpoints WITHOUT token
        if (path.startsWith("/api/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);

            try {
                String username = jwtUtil.extractUsername(token);

                if (username != null &&
                        SecurityContextHolder.getContext().getAuthentication() == null) {

                    // 🔥 ADD THIS BLOCK HERE
                    String role = jwtUtil.extractRole(token);

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    username,
                                    null,
                                    List.of(new SimpleGrantedAuthority("ROLE_" + role))
                            );

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }

            } catch (Exception e) {
                System.out.println("Invalid JWT: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }**/

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // 🔥 SKIP JWT for these paths
        if (path.startsWith("/api/auth") ||
                path.equals("/") ||
                path.equals("/index.html") ||
                path.startsWith("/static") ||
                path.startsWith("/error")) {

            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");

        try {
            if (header != null && header.startsWith("Bearer ")) {

                String token = header.substring(7);

                String username = jwtUtil.extractUsername(token);
                String role = jwtUtil.extractRole(token);

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                List.of(new SimpleGrantedAuthority("ROLE_" + role))
                        );

                SecurityContextHolder.getContext().setAuthentication(auth);
            }

        } catch (Exception e) {
            // 🔥 NEVER throw
            System.out.println("JWT error: " + e.getMessage());
        }

        // 🔥 ALWAYS continue
        filterChain.doFilter(request, response);
    }
}

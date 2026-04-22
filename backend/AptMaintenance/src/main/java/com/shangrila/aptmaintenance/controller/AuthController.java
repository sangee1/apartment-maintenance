package com.shangrila.aptmaintenance.controller;

import com.shangrila.aptmaintenance.dto.AuthRequest;
import com.shangrila.aptmaintenance.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody AuthRequest req) {

        if ("admin".equals(req.getUsername()) &&
                "admin123".equals(req.getPassword())) {

            String token = jwtUtil.generateToken("admin", "ADMIN");
            return Map.of("token", token);
        }

        if ("user".equals(req.getUsername()) &&
                "user123".equals(req.getPassword())) {

            String token = jwtUtil.generateToken("user", "RESIDENT");
            return Map.of("token", token);
        }

        throw new RuntimeException("Invalid credentials");
    }
}

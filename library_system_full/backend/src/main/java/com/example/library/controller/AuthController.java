package com.example.library.controller;

import com.example.library.config.JwtUtil;
import com.example.library.dto.LoginDto;
import com.example.library.dto.RegisterDto;
import com.example.library.model.User;
import com.example.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired private UserService userService;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Validated @RequestBody RegisterDto dto) {
        userService.register(dto);
        return ResponseEntity.ok().body(java.util.Map.of("status","registered"));
    }

@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto dto) {
        try {
            User u = userService.login(dto.getPhone(), dto.getPassword());
            String token = jwtUtil.generateToken(u.getPhoneNumber(), u.getUserId());
            return ResponseEntity.ok(java.util.Map.of(
                "token", token,
                "userId", u.getUserId()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401)
                    .body(java.util.Map.of("message", e.getMessage()));
        }
    }
}

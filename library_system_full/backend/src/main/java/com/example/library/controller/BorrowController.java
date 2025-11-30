package com.example.library.controller;

import com.example.library.dto.BorrowDto;
import com.example.library.service.BorrowService;
import com.example.library.repository.UserRepository;
import com.example.library.model.User;
import com.example.library.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/borrow")
public class BorrowController {
    @Autowired private BorrowService borrowService;
    @Autowired private UserRepository userRepository;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/do")
    public ResponseEntity<?> borrow(@RequestBody BorrowDto dto, Authentication auth, @RequestHeader(value = "Authorization", required = false) String authHeader) {
        Long userId = resolveUserId(auth, authHeader);
        borrowService.borrow(userId, dto.getInventoryId());
        return ResponseEntity.ok().body(java.util.Map.of("status","borrowed"));
    }

    @PostMapping("/return")
    public ResponseEntity<?> ret(@RequestBody BorrowDto dto, Authentication auth, @RequestHeader(value = "Authorization", required = false) String authHeader) {
        Long userId = resolveUserId(auth, authHeader);
        borrowService.returnBook(userId, dto.getInventoryId());
        return ResponseEntity.ok().body(java.util.Map.of("status","returned"));
    }

    private Long resolveUserId(Authentication auth, String authHeader) {
        // try token first
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            Long id = jwtUtil.extractUserId(token);
            if (id != null) return id;
        }
        // fallback: try auth principal (phone) and lookup id
        if (auth != null && auth.getName() != null) {
            User u = userRepository.findByPhone(auth.getName());
            if (u != null) return u.getUserId();
        }
        throw new RuntimeException("Cannot resolve user id");
    }
}

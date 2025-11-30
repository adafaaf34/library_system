package com.example.library.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.library.repository.UserRepository;
import com.example.library.model.User;

import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    @Autowired private JwtUtil jwtUtil;
    @Autowired private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            logger.debug("JwtFilter: received token starts with Bearer");
            Long userId = jwtUtil.extractUserId(token);

            if (userId != null) {
                logger.debug("JwtFilter: extracted userId={}", userId);
                User user = userRepository.findById(userId);
                if (user != null) {
                    UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                            user.getPhoneNumber(),
                            null,
                            List.of()  // 權限清單，現在空
                        );
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    logger.debug("JwtFilter: authentication set for user {}", user.getPhoneNumber());
                } else {
                    logger.debug("JwtFilter: user not found for id {}", userId);
                }
            } else {
                logger.debug("JwtFilter: cannot extract userId from token");
            }
        } else {
            // no Authorization header
            //logger.debug("JwtFilter: no Authorization header");
        }

        filterChain.doFilter(request, response);
    }
}

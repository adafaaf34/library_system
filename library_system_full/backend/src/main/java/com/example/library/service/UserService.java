package com.example.library.service;

import com.example.library.dto.RegisterDto;
import com.example.library.model.User;
import com.example.library.repository.UserRepository;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {
    @Autowired private UserRepository userRepo;
    @Autowired private PasswordEncoder passwordEncoder;

    private String genSalt() {
        byte[] b = new byte[16]; new SecureRandom().nextBytes(b); return Base64.getEncoder().encodeToString(b);
    }

    @Transactional
    public void register(RegisterDto dto) {
        String phone = Jsoup.clean(dto.getPhone(), Safelist.none());
        String name = Jsoup.clean(dto.getUserName()==null? "": dto.getUserName(), Safelist.none());

        if (userRepo.existsByPhone(phone)) throw new RuntimeException("Phone already registered");

        String salt = genSalt();
        String hashed = passwordEncoder.encode(dto.getPassword() + salt);
        userRepo.insertUser(phone, hashed, salt, name);
    }

    public User login(String phone, String password) {
        User u = userRepo.findByPhone(phone);
        if (u == null) throw new RuntimeException("Invalid credentials");
        String salted = password + u.getSalt();
        if (!passwordEncoder.matches(salted, u.getPasswordHash())) throw new RuntimeException("Invalid credentials");
        userRepo.updateLastLogin(u.getUserId());
        return u;
    }
}
